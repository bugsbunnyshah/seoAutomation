package io.seo.endpoint;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ConsumeSiteContentTests {
    private Logger logger = LoggerFactory.getLogger(ConsumeSiteContentTests.class);

    @Test
    public void testConsume() throws Exception
    {
        String jsonString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("siteContent.json"),
                StandardCharsets.UTF_8);
        Response response = given().body(jsonString).post("/consume/content/seven")
                .andReturn();

        String jsonResponse = response.getBody().prettyPrint();
        logger.info("****");
        logger.info(response.getStatusLine());
        logger.info(jsonResponse);
        logger.info("****");

        response = given().get("/results/calculate/seven")
                .andReturn();

        jsonResponse = response.getBody().prettyPrint();
        logger.info("****");
        logger.info(response.getStatusLine());
        logger.info(jsonResponse);
        logger.info("****");
    }

}
