package io.seo.parser;

import io.quarkus.test.junit.QuarkusTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class JsoupParserTests {
    private static Logger logger = LoggerFactory.getLogger(JsoupParserTests.class);

    @Test
    public void testJsoup() throws Exception
    {
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        logger.info(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines)
        {
            logger.info(headline.attr("title")+":"+headline.absUrl("href"));
        }
    }

}
