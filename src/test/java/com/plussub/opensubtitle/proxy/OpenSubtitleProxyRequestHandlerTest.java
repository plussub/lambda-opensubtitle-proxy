package com.plussub.opensubtitle.proxy;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by sonste on 26.02.2017.
 */
public class OpenSubtitleProxyRequestHandlerTest {

    @Test(expected=InvalidRequestException.class)
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

    @Test
    public void remove_imdbid_tt_prefix() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setImdbid("tt123");
        assertThat(searchRequest.getImdbid(),is("123"));
    }

    @Test
    public void imdbid_without_prefix_should_be_forwarded() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setImdbid("123");
        assertThat(searchRequest.getImdbid(),is("123"));
    }

    @Test(expected=InvalidRequestException.class)
    public void imdbid_with_character_should_throw_an_exception() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setImdbid("ttasdf");
    }

    @Test(expected=InvalidRequestException.class)
    public void missing_imdbid_should_throw_an_exception() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setImdbid("");
    }


}