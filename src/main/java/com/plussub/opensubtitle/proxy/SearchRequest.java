package com.plussub.opensubtitle.proxy;

import java.util.Arrays;
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
        String validImdbId = imdbid.toLowerCase().replace("t","");
        boolean containsAlphabeticChar = validImdbId.chars().mapToObj(c -> (char)c).anyMatch(c -> Character.isAlphabetic(c));
        if(containsAlphabeticChar){
            throw new InvalidRequestException("unsupported imdbd format. supported: <tt><number> where <tt> is optional");
        }

        this.imdbid = validImdbId;
    }

    public String getIso639LanguageCode() {
        return iso639LanguageCode;
    }

    public void setIso639LanguageCode(String iso639LanguageCode) {

        if(!existsLanguageIso639Code(iso639LanguageCode)){
            throw new InvalidRequestException("Unknown language code");
        }

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