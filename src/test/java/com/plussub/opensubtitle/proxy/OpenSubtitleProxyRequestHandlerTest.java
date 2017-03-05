package com.plussub.opensubtitle.proxy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sonste on 26.02.2017.
 */
public class OpenSubtitleProxyRequestHandlerTest {

    @Test(expected=InvalidSearchRequestException.class)
    public void invalid_language_codes_should_throw_an_exception() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setIso639LanguageCode("asdf");
    }

    @Test
    public void valid_language_codes() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setIso639LanguageCode("eng");
        searchRequest.setIso639LanguageCode("pob"); //Portuguese Brazilian
    }


}