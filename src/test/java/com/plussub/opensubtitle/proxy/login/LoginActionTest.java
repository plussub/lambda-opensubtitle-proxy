package com.plussub.opensubtitle.proxy.login;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by sonste on 26.02.2017.
 */
public class LoginActionTest {

    @Bind
    private RpcClient rpcClientMock;

    @Inject
    private LoginAction loginAction;


    @Test(expected = LoginException.class)
    public void expect_failed_login() throws Exception {

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);
        expect(rpcClientMock.execute(eq(LoginAction.RPC_METHOD_NAME),eq(Lists.newArrayList("","","en","PlusSub")))).andReturn(Maps.newHashMap());

        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);

        mocksControl.replay();
        loginAction.execute(null);
        mocksControl.verify();
    }

    @Test
    public void expect_login_token() throws Exception {

        final String expectedToken = "aToken";
        Map<String,Object> expectedMap = Maps.newHashMap();
        expectedMap.put("token",expectedToken);

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);
        expect(rpcClientMock.execute(eq(LoginAction.RPC_METHOD_NAME),eq(Lists.newArrayList("","","en","PlusSub")))).andReturn(expectedMap);

        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);


        mocksControl.replay();
        LoginToken loginToken = loginAction.execute(null);
        mocksControl.verify();

        assertThat(loginToken,is(not(nullValue())));
        assertThat(loginToken.getToken(),is(expectedToken));
    }

}