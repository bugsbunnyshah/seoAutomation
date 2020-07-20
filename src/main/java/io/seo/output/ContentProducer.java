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

    public List<SEOResult> produceFinalContent()
    {
        try
        {
            List<SEOResult> finalContent = new ArrayList<>();

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
                }
                ranking = this.sortByValue(ranking);
                logger.info(ranking.toString());
                rankings.add(ranking);

                //Produce the title
                String[] tempTitle = ranking.keySet().toArray(new String[0]);
                String[] title = new String[3];
                title[0] = tempTitle[0];
                title[1] = tempTitle[1];
                title[2] = tempTitle[2];

                //Produce the description
                String[] description = ranking.keySet().toArray(new String[0]);

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
