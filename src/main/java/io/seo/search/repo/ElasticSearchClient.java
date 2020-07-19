package io.seo.search.repo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> search() throws Exception
    {
        try
        {
            List<String> results = new ArrayList<>();

            String query = "{\n" +
                    "\"query\": { \"match_all\": {} }\n" +
                    "}";
            HttpClient httpClient = HttpClient.newBuilder().build();
            String requestUrl = "http://localhost:9200/seo/_search/";
            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder();
            HttpRequest httpRequest = httpRequestBuilder.uri(new URI(requestUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(query))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseJson = httpResponse.body();
            JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
            JsonArray arrayResults = jsonObject.get("hits").getAsJsonObject().get("hits").getAsJsonArray();

            int length = arrayResults.size();
            for(int i=0; i<length; i++)
            {
                JsonObject local = arrayResults.get(i).getAsJsonObject();
                String content = local.get("_source").getAsJsonObject().get("data").getAsString();
                results.add(content);
            }

            return results;
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
