package com.plussub.opensubtitle.proxy;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.plussub.opensubtitle.proxy.login.LoginAction;
import com.plussub.opensubtitle.proxy.login.LoginToken;
import com.plussub.opensubtitle.proxy.search.SearchAction;
import com.plussub.opensubtitle.proxy.search.SearchCriteria;

import java.util.List;
import java.util.Map;

public class OpenSubtitleProxyRequestHandler implements RequestHandler<SearchRequest, List<Map<String, String>>> {

    public List<Map<String, String>> handleRequest(SearchRequest request, Context context) {
        Injector injector = Guice.createInjector(new Module());
        LoginToken loginToken = injector.getInstance(LoginAction.class).execute(null);
        SearchAction searchAction = injector.getInstance(SearchAction.class);
        return  searchAction.execute(new SearchCriteria(loginToken, request.getImdbid(), request.getIso639LanguageCode()));
    }
}