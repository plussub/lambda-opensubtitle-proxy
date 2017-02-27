package com.plussub.opensubtitle.proxy;

import com.google.inject.AbstractModule;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;
import com.plussub.opensubtitle.proxy.rpc.RpcClientFactory;

/**
 * Created by sonste on 26.02.2017.
 */
public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(RpcClient.class).toInstance(new RpcClientFactory.OpenSubtitleXmlRpcClientFactory().create());
    }
}
