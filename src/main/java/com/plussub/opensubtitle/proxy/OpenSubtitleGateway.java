package com.plussub.opensubtitle.proxy;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sonste on 26.02.2017.
 */
public class OpenSubtitleGateway {

    private final RpcClient rpcClient;

    @Inject
    public OpenSubtitleGateway(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }


    void login() {

        Object result = rpcClient.execute("LogIn", Lists.newArrayList("", "", "en", "PlusSub"));
        System.out.println(result);

    }

}
