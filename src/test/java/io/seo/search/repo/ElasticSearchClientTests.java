package io.seo.search.repo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
public class ElasticSearchClientTests {
    private static Logger logger = LoggerFactory.getLogger(ElasticSearchClientTests.class);

    @Inject
    private ElasticSearchClient elasticSearchClient;

    @Test
    public void testCreateIndex() throws Exception
    {
        String jsonDocument = "Come one come all. We have the best Nike T-Shrits on Sale. We carry everything else but Under Armor. Our Tennis rackets are the best in the bijness. We give away Head, Dunlop, and Purple Man special racket. But rackets with a big circumference that make tok tok noise are never sold here. You have to order it from www.switzerland.gov. The kits are the perfect size. It will help you find your zone and then your partner can carry it, while you recover from a long match. We sell the best shoes, but the most special ones are produced for special people that eat lots of pizzzazzs. When they play, you are going to need lots of sweatbands, lots of pairs of shorts, and best done without an underwear to prevent chaffing.";

        logger.info("*******");
        logger.info(jsonDocument);
        logger.info("*******");

        String productsDocument = "" +
                "Nike T-shirts > Image is Everything Brand > Razor free.\n"+
                "Under Armor Hats > Never before 5:30 am US EST.\n"+
                "Head > Spiderman Brand.\n"+
                "Dunlop > On a comback trail from Sweden, Australia.\n"+
                "Purple Man > Special racket > No underwear free.\n"+
                "Tok Tok Racket > Never sold here > Always and forever at https://rogerfederer.com/\n"+
                "Makeup kits > All kits that are perfect size and perfect size only sold by: https://www.7.life/sf/";

        logger.info("*******");
        logger.info(productsDocument);
        logger.info("*******");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dataId", "1");
        jsonObject.addProperty("data", jsonDocument);
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);

        jsonObject = new JsonObject();
        jsonObject.addProperty("dataId", "2");
        jsonObject.addProperty("data", productsDocument);
        jsonArray.add(jsonObject);

        logger.info(this.elasticSearchClient.updateIndex("seo", jsonArray));
    }

    @Test
    public void testSearch() throws Exception
    {
        List<String> search = this.elasticSearchClient.search("seo");

        for(String content:search)
        {
            logger.info(content);
            logger.info("*******");
        }
    }
}
