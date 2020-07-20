package io.seo.input;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.seo.search.repo.CustomerAssets;
import io.seo.search.repo.ElasticSearchClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class ContentConsumer
{
    private static Logger logger = LoggerFactory.getLogger(ContentConsumer.class);

    @Inject
    private CustomerAssets customerAssets;

    @Inject
    private ElasticSearchClient elasticSearchClient;

    public void consumeContent(JsonObject jsonObject)
    {
        try
        {
            String siteId = jsonObject.get("siteId").getAsString();
            JsonArray keywords = jsonObject.get("keywords").getAsJsonArray();
            JsonArray urls = jsonObject.get("urls").getAsJsonArray();

            int length = urls.size();
            for(int i=0; i<length; i++)
            {
                Document doc = Jsoup.connect(urls.get(i).getAsString()).get();
                String html = doc.html();

                JsonObject indexJson = new JsonObject();
                indexJson.addProperty("dataId", UUID.randomUUID().toString());
                indexJson.addProperty("data", html);

                JsonArray jsonArray = new JsonArray();
                jsonArray.add(indexJson);

                String response = this.elasticSearchClient.updateIndex(siteId, jsonArray);
                //logger.info(response);
            }

            //Update the keyword soup
            Map<String, String> keywordSoup = new LinkedHashMap<>();
            length = keywords.size();
            for(int i=0; i<length; i++)
            {
                String keyword = keywords.get(i).getAsString();
                keywordSoup.put(keyword, keyword);
            }
            this.customerAssets.setKeywords(siteId, keywordSoup);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
