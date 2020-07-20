package io.seo.input;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.quarkus.test.junit.QuarkusTest;
import io.seo.output.ContentProducer;
import io.seo.output.SEOResult;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.List;

@QuarkusTest
public class ContentConsumerTests {
    private static Logger logger = LoggerFactory.getLogger(ContentConsumerTests.class);

    @Inject
    private ContentConsumer contentConsumer;

    @Test
    public void testConsumeContent() throws Exception
    {
        String jsonString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("siteContent.json"),
                StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        this.contentConsumer.consumeContent(jsonObject);
    }
}
