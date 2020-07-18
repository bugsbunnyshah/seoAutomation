package io.seo.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupParserPrototype {
    private static Logger logger = LoggerFactory.getLogger(JsoupParserPrototype.class);

    public static void main(String[] args) throws Exception
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
