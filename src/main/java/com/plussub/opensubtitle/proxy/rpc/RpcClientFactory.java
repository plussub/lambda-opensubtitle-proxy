package com.plussub.opensubtitle.proxy.rpc;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.TypeFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sonste on 26.02.2017.
 */
public interface RpcClientFactory {

    RpcClient create();

    class OpenSubtitleXmlRpcClientFactory implements RpcClientFactory {

        private final static URL OPEN_SUBTITLE_RPC_URL;

        static {
            try {
                OPEN_SUBTITLE_RPC_URL = new URL("http://api.opensubtitles.org/xml-rpc");
            } catch (MalformedURLException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        public com.plussub.opensubtitle.proxy.rpc.RpcClient.XmlRpcClient create() {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(OPEN_SUBTITLE_RPC_URL);
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);

            return new com.plussub.opensubtitle.proxy.rpc.RpcClient.XmlRpcClient(client);
        }
    }
}
