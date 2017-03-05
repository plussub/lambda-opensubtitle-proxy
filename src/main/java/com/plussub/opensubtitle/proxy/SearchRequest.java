package com.plussub.opensubtitle.proxy;

import java.util.Locale;

import static com.plussub.opensubtitle.proxy.SupportedLanguages.existsLanguageIso639Code;

/**
 * Created by sonste on 26.02.2017.
 */
public class SearchRequest {
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

        if(!existsLanguageIso639Code(iso639LanguageCode)){
            throw new InvalidSearchRequestException("Unknown language code");
        }

        Locale.forLanguageTag(iso639LanguageCode);
        this.iso639LanguageCode = iso639LanguageCode;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "imdbid='" + imdbid + '\'' +
                ", iso639LanguageCode='" + iso639LanguageCode + '\'' +
                '}';
    }
}