package com.url.shortener.services.manager;

import com.url.shortener.entry.Url;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UrlManager {
    public String getUrlByKey(String key);
    public Url shortenUrl(String url);
}
