package com.example.UrlShortner.URLshortner.ServiceImple;

import com.example.UrlShortner.URLshortner.Models.UrlEntry;

import java.security.InvalidKeyException;

public interface Srviceimple {
    public UrlEntry getOriginalUrlFromRedis(String ShortUrl);

    public UrlEntry UrlEntrytoDb(String urlvalue) throws InvalidKeyException;
    public UrlEntry GetOriginalDb (String decodedUrl);
}
