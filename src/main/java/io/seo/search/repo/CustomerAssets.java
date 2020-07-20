package io.seo.search.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class CustomerAssets implements Serializable
{
    private static Logger logger = LoggerFactory.getLogger(CustomerAssets.class);

    private Map<String,String> keywords;

    public CustomerAssets()
    {
        this.keywords = new LinkedHashMap<>();

        this.keywords.put("shoes", "shoes");
        this.keywords.put("racket", "racket");
        this.keywords.put("kit", "kit");
        this.keywords.put("socks", "socks");
        this.keywords.put("sweatband", "sweatband");
        this.keywords.put("t-shirt", "t-shirt");
        this.keywords.put("tshirt", "tshirt");
        this.keywords.put("shorts", "shorts");
    }

    public Map<String, String> getKeywords()
    {
        return keywords;
    }

    public void setKeywords(Map<String, String> keywords)
    {
        this.keywords = keywords;
    }
}
