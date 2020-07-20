package io.seo.search.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.*;

@Singleton
public class CustomerAssets implements Serializable
{
    private static Logger logger = LoggerFactory.getLogger(CustomerAssets.class);

    private Map<String, Map<String,String>> customerData;

    public CustomerAssets()
    {
        this.customerData = new LinkedHashMap<>();
        Map<String, String> keywords = new LinkedHashMap<>();

        keywords.put("shoes", "shoes");
        keywords.put("racket", "racket");
        keywords.put("kit", "kit");
        keywords.put("socks", "socks");
        keywords.put("sweatband", "sweatband");
        keywords.put("t-shirt", "t-shirt");
        keywords.put("tshirt", "tshirt");
        keywords.put("shorts", "shorts");

        this.customerData.put("seo", keywords);
    }

    public Map<String, String> getKeywords(String customerId)
    {
        return this.customerData.get(customerId);
    }

    public void setKeywords(String customerId, Map<String, String> keywords)
    {
        this.customerData.put(customerId, keywords);
    }
}
