package io.seo.output;

import io.quarkus.test.junit.QuarkusTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
public class ContentProducerTests {
    private static Logger logger = LoggerFactory.getLogger(ContentProducerTests.class);

    @Inject
    private ContentProducer contentProducer;

    @Test
    public void testProduceFinalContent() throws Exception
    {
        List<SEOResult> finalContent = this.contentProducer.produceFinalContent("seo");
        logger.info("ResultSize: "+finalContent.size());
        for(SEOResult seoResult: finalContent)
        {
            logger.info("*******");
            logger.info(seoResult.finalContent());
            logger.info("*******");
        }
    }
}
