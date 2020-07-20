package io.seo.output;

import io.seo.search.repo.CustomerAssets;
import io.seo.search.repo.ElasticSearchClient;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

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

    public List<SEOResult> produceFinalContent(String siteId)
    {
        try
        {
            List<SEOResult> finalContent = new ArrayList<>();

            Map<String,String> keywords = this.customerAssets.getKeywords(siteId);
            if(keywords == null || keywords.isEmpty())
            {
                return finalContent;
            }

            List<String> search = this.elasticSearchClient.search(siteId);
            List<Map<String, Integer>> rankings = new ArrayList<>();

            for (String content : search)
            {
                Map<String, Integer> ranking = new LinkedHashMap<>();
                String data = content.toLowerCase(Locale.getDefault());
                Set<Map.Entry<String,String>> entrySet = keywords.entrySet();
                for(Map.Entry<String,String> entry:entrySet)
                {
                    String keyword = entry.getValue();
                    String plainText= Jsoup.parse(data).text();
                    //logger.info(plainText);
                    int count = StringUtils.countMatches(plainText, keyword);
                    if(count > 0)
                    {
                        ranking.put(keyword, count);
                    }
                }
                ranking = this.sortByValue(ranking);
                logger.info("**************************************************************");
                logger.info("RANKING: "+ranking.toString());
                logger.info("**************************************************************");
                rankings.add(ranking);

                //Produce the description
                String[] description = ranking.keySet().toArray(new String[0]);

                //Produce the title
                int titleLength = 3;
                if(description.length < 3)
                {
                   titleLength = description.length;
                }
                String[] title = new String[titleLength];
                for(int i=0; i<titleLength; i++)
                {
                    title[i] = description[i];
                }

                SEOResult seoResult = new SEOResult(title,description,content);
                finalContent.add(seoResult);
            }

            return finalContent;
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
