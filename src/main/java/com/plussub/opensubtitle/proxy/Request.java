package com.plussub.opensubtitle.proxy;

/**
 * Created by sonste on 26.02.2017.
 */
public class Request {
    private String imdbid;
    private String iso639LanguageCode;

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getIso639LanguageCode() {
        return iso639LanguageCode;
    }

    public void setIso639LanguageCode(String iso639LanguageCode) {
        this.iso639LanguageCode = iso639LanguageCode;
    }

    @Override
    public String toString() {
        return "Request{" +
                "imdbid='" + imdbid + '\'' +
                ", iso639LanguageCode='" + iso639LanguageCode + '\'' +
                '}';
    }
}