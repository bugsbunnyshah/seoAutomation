package io.seo.endpoint;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import io.seo.output.ContentProducer;
import io.seo.output.SEOResult;

@Path("results")
public class GetResults {
    private Logger logger = LoggerFactory.getLogger(GetResults.class);

    @Inject
    private ContentProducer contentProducer;

    @Path("calculate/{customerId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(@PathParam("customerId") String customerId) throws Exception
    {
        try {
            List<SEOResult> finalContent = this.contentProducer.produceFinalContent(customerId);

            JsonArray results = new JsonArray();
            for(SEOResult seoResult: finalContent)
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", seoResult.finalContent());
                results.add(jsonObject);
            }
            return Response.ok(results.toString()).build();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", e.getMessage());
            return Response.status(500).entity(jsonObject.toString()).build();
        }
    }

}
