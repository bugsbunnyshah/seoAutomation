package io.seo.search.repo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class ElasticSearchClient {
    private static Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class);

    public String updateIndex(JsonArray jsonDocs)
    {
        try
        {
            StringBuilder responseBuilder = new StringBuilder();

            HttpClient httpClient = HttpClient.newBuilder().build();
            int length = jsonDocs.size();
            for(int i=0; i<length; i++)
            {
                JsonObject jsonObject = jsonDocs.get(i).getAsJsonObject();
                String dataId = jsonObject.get("dataId").getAsString();
                String dataString = jsonObject.toString();
                String requestUrl = "http://localhost:9200/seo/_doc/"+dataId+"/";
                HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder();
                HttpRequest httpRequest = httpRequestBuilder.uri(new URI(requestUrl))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(dataString))
                        .build();
                HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                responseBuilder.append(httpResponse.body()+"\n");
            }
            return responseBuilder.toString();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
