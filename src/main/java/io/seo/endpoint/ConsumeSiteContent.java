package io.seo.endpoint;

import com.google.gson.JsonObject;

import com.google.gson.JsonParser;
import io.seo.input.ContentConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("consume")
public class ConsumeSiteContent {
    private Logger logger = LoggerFactory.getLogger(ConsumeSiteContent.class);

    @Inject
    private ContentConsumer contentConsumer;

    @Path("content/{siteJson}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response consume(String siteJson) throws Exception
    {
        try
        {
            JsonObject jsonObject = JsonParser.parseString(siteJson).getAsJsonObject();
            this.contentConsumer.consumeContent(jsonObject);

            return Response.ok().build();
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
