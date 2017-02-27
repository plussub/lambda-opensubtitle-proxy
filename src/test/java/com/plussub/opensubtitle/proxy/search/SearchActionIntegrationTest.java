package com.plussub.opensubtitle.proxy.search;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import com.plussub.opensubtitle.proxy.login.LoginAction;
import com.plussub.opensubtitle.proxy.login.LoginToken;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;
import com.plussub.opensubtitle.proxy.rpc.RpcClientFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by sonste on 27.02.2017.
 */
public class SearchActionIntegrationTest {

    @Bind
    private RpcClient rpcClientMock;

    @Inject
    private SearchAction searchAction;

    @Inject
    private LoginAction loginAction;


    @Test
    public void opensubtitle_integration_test() throws Exception {

        rpcClientMock = new RpcClientFactory.OpenSubtitleXmlRpcClientFactory().create();

        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);

        LoginToken loginToken = loginAction.execute(null);
        System.out.println(searchAction.execute(new SearchCriteria(loginToken,"0110912","eng")).size());
    }
}