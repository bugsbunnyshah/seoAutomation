package io.seo.search.repo;


import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@QuarkusTest
public class CustomerAssetsTests {
    private static Logger logger = LoggerFactory.getLogger(CustomerAssetsTests.class);

    @Test
    public void testGetKeywords() throws Exception
    {
        CustomerAssets customerAssets = new CustomerAssets();
        Map<String,String> keywords = customerAssets.getKeywords();
        logger.info("*******");
        logger.info(keywords.toString());
        logger.info("*******");
    }
}
