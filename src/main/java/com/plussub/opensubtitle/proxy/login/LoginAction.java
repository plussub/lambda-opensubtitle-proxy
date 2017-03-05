package com.plussub.opensubtitle.proxy.login;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.plussub.opensubtitle.proxy.Criteria;
import com.plussub.opensubtitle.proxy.OpenSubtitleAction;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;

import java.util.Map;

/**
 * Created by sonste on 26.02.2017.
 */
public class LoginAction implements OpenSubtitleAction<LoginToken,Criteria.VoidCriteria>{

    private final RpcClient rpcClient;
    private final static String USERNAME = "";
    private final static String PASSWORD = "";
    private final static String TOKEN_PARAM="token";
    static final String RPC_METHOD_NAME ="LogIn";

    @Inject
    public LoginAction(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    public LoginToken execute(Criteria.VoidCriteria v) {

        Map<String,String> result = rpcClient.execute(RPC_METHOD_NAME, Lists.newArrayList(USERNAME, PASSWORD, "en", "PlusSub"));

        if(!result.containsKey(TOKEN_PARAM)){
            throw new LoginException("missing token parameter");
        }

        return new LoginToken(result.get(TOKEN_PARAM));
    }
}
