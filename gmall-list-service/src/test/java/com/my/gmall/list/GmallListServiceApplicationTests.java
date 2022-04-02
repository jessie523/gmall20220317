package com.my.gmall.list;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallListServiceApplicationTests {


    @Autowired
    private JestClient jestClient;

    /**
     * 1 定义dsl语句
     * 2 定义执行的动作
     * 3 执行动作
     * 4 获取执行后的结果集
     * @throws IOException
     */
    @Test
    public void testEs() throws IOException {
        String query="{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"actorList.name\": \"张译\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        System.out.println(query);
        //定义动作---查询
        Search search = new Search.Builder(query).addIndex("movie_chn").addType("movie").build();

        //执行动作
        SearchResult searchResult = jestClient.execute(search);

        List<SearchResult.Hit<HashMap, Void>> hits = searchResult.getHits(HashMap.class);

        for(SearchResult.Hit<HashMap, Void> hit:hits){
            HashMap source = hit.source;
            System.out.println(source);

        }

    }

}
