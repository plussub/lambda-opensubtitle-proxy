package com.plussub.opensubtitle.proxy.rpc;

import org.apache.xmlrpc.XmlRpcException;

import java.util.List;
import java.util.Map;

/**
 * Created by sonste on 26.02.2017.
 */
public interface RpcClient {
     <T> Map<String,T> execute(String pMethodName, List<?> pParams);

    class XmlRpcClient implements RpcClient {

        private org.apache.xmlrpc.client.XmlRpcClient client;

         XmlRpcClient(org.apache.xmlrpc.client.XmlRpcClient client) {
            this.client = client;
        }

        @SuppressWarnings("unchecked")
        public <T> Map<String,T> execute(String pMethodName, List<?> pParams) {
            try {
                return (Map<String,T>) client.execute(pMethodName, pParams);
            } catch (XmlRpcException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
