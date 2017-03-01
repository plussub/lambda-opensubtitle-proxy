package com.plussub.opensubtitle.proxy;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.plussub.opensubtitle.proxy.login.LoginAction;
import com.plussub.opensubtitle.proxy.login.LoginToken;
import com.plussub.opensubtitle.proxy.search.SearchAction;
import com.plussub.opensubtitle.proxy.search.SearchCriteria;

import java.util.Collections;
import java.util.Map;

public class OpenSubtitleProxyRequestHandler implements RequestHandler<Request, Map<String, Object>> {

    public Map<String, Object> handleRequest(Request request, Context context) {
        Injector injector = Guice.createInjector(new Module());
        LoginToken loginToken = injector.getInstance(LoginAction.class).execute(null);
        SearchAction searchAction = injector.getInstance(SearchAction.class);

        Map<String,Object> proxy = Maps.newHashMap();
        proxy.put("statusCode",200);
        proxy.put("headers", Collections.emptyList());
        proxy.put("body",searchAction.execute(new SearchCriteria(loginToken, "0110912", "eng")));

        return proxy;
    }
}