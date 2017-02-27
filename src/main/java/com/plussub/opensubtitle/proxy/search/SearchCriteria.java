package com.plussub.opensubtitle.proxy.search;

import com.plussub.opensubtitle.proxy.login.LoginToken;

/**
 * Created by sonste on 27.02.2017.
 */
public class SearchCriteria {

    private final String imdbId;

    private final String isoLanguageCode;

    private final LoginToken loginToken;

    public SearchCriteria(LoginToken loginToken, String imdbId, String isoLanguageCode ){
        this.loginToken = loginToken;
        this.imdbId = imdbId;
        this.isoLanguageCode = isoLanguageCode;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getIsoLanguageCode() {
        return isoLanguageCode;
    }

    public LoginToken getLoginToken() {
        return loginToken;
    }

}
