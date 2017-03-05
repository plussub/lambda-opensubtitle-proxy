package com.plussub.opensubtitle.proxy;

/**
 * Created by sonste on 26.02.2017.
 */
public interface OpenSubtitleAction<T, U extends Criteria> {
    String STATUS_OK = "200 OK";

    T execute(U criteria);
}
