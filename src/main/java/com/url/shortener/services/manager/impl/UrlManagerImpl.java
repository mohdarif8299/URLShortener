package com.url.shortener.services.manager.impl;

import com.google.common.hash.Hashing;
import com.url.shortener.entry.Url;
import com.url.shortener.services.manager.UrlManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UrlManagerImpl implements UrlManager {
    @Autowired
    private RedisTemplate<String, Url> redisTemplate;

    @Override
    public String getUrlByKey( String key) {
        Url url = redisTemplate.opsForValue().get(key);
        return url.getUrl();
    }

    @Override
    public Url shortenUrl(String url) {
        // generating murmur3 based hash key as short URL
        String key = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();

        System.out.println(key);

        Url shortUrlEntry = Url.builder().key(key).createdAt(LocalDateTime.now()).url(url).build();

        //store in redis
        redisTemplate.opsForValue().set(key, shortUrlEntry, 36000L, TimeUnit.SECONDS);

        return shortUrlEntry;
    }
}
