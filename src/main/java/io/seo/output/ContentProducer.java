package io.seo.output;

import io.seo.search.repo.CustomerAssets;
import io.seo.search.repo.ElasticSearchClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class ContentProducer {
    private static Logger logger = LoggerFactory.getLogger(ContentProducer.class);

    @Inject
    private ElasticSearchClient elasticSearchClient;

    @Inject
    private CustomerAssets customerAssets;

    public List<String> produceFinalContent()
    {
        try
        {
            List<String> finalContent = new ArrayList<>();

            List<String> search = this.elasticSearchClient.search();
            Map<String,String> keywords = this.customerAssets.getKeywords();
            List<Map<String, Integer>> rankings = new ArrayList<>();

            for (String content : search)
            {
                Map<String, Integer> ranking = new LinkedHashMap<>();
                String data = content.toLowerCase(Locale.getDefault());
                Set<Map.Entry<String,String>> entrySet = keywords.entrySet();
                for(Map.Entry<String,String> entry:entrySet)
                {
                    String keyword = entry.getValue();
                    int count = StringUtils.countMatches(data, keyword);
                    ranking.put(keyword, count);
                    logger.info("Keyword: "+keyword+", Count: "+count);
                }
                rankings.add(ranking);
            }

            //TODO: Sort rankings by value



            return finalContent;
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
