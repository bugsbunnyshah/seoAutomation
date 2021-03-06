package io.seo.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;

public class SEOResult implements Serializable{
    private static Logger logger = LoggerFactory.getLogger(SEOResult.class);

    private String[] title;
    private String[] description;
    private String originalContent;

    public SEOResult(String[] title, String[] description, String originalContent) {
        this.title = title;
        this.description = description;
        this.originalContent = originalContent;
    }

    public String[] getTitle()
    {
        return title;
    }

    public void setTitle(String[] title)
    {
        this.title = title;
    }

    public String[] getDescription()
    {
        return description;
    }

    public void setDescription(String[] description)
    {
        this.description = description;
    }

    public String getOriginalContent()
    {
        return originalContent;
    }

    public void setOriginalContent(String originalContent)
    {
        this.originalContent = originalContent;
    }

    public String finalContent()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<title value=\""+ Arrays.toString(this.title)+"\">");
        stringBuilder.append("<description value=\""+ Arrays.toString(this.description)+"\">");
        //stringBuilder.append(this.originalContent);

        return stringBuilder.toString();
    }
}
