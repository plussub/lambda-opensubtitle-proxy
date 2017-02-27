package com.plussub.opensubtitle.proxy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sonste on 26.02.2017.
 */
public class OpenSubtitleProxyRequestHandlerTest {

    @Test
    public void test() throws Exception {
        OpenSubtitleProxyRequestHandler requestHandler = new OpenSubtitleProxyRequestHandler();
        System.out.println(requestHandler.handleRequest(new Request(), null));

        //        gateway.login();
    }
}