package com.cn.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.entity.Goods;
import com.cn.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/27 14:52
 */
@Service
@org.springframework.stereotype.Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> searchByKey(String keyword) {

        SolrQuery solrQuery =null;
        //关键字不存在查询所有索引库内容
        if (keyword == null || keyword.trim().equals("")) {
            solrQuery = new SolrQuery("*:*");
        }else {
         //关键字存在，则查询指定索引库内容
            String str = "gname:%s || ginfo:%s";
            String s = String.format(str, keyword, keyword);
            solrQuery = new SolrQuery(s);

        }

        //设置高亮开启
        solrQuery.setHighlight(true);
        //设置高亮内容样式
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //设置高亮字段
        solrQuery.addHighlightField("gname");

        //存放查询出来的商品结果，有高亮有不是高亮的
        List<Goods> goodsList = new ArrayList<>();

        try {
            //进行查询获取响应对象
            QueryResponse queryResponse = solrClient.query(solrQuery);
            //普通查询结果（不含高亮）
            SolrDocumentList results = queryResponse.getResults();
            //获取需要高亮显示内容的查询结果
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            for (SolrDocument result : results) {
                Goods goods = new Goods();
                //result.getFieldValue()类型是object类型，任何类型加“”都是字符串类型
                //此处索引库中的id默认是保存为string类型，所以需要转为Integer类型
                int id = Integer.parseInt(result.getFieldValue("id") + "");
                String gname = result.getFieldValue("gname") + "";
                String gimage = result.getFieldValue("gimage") + "";
                BigDecimal gprice = new BigDecimal(result.getFieldValue("gprice") + "");
                //此处是因为该字段在索引库中定义的类型就是int类似，所以直接指定int就好
                int gsave = (int)result.getFieldValue("gsave");
                //将查询出来的结果放入goods对象中，该结果可能包含有高亮也有可能不含高亮，例如ginfo参与查询条件
                //但是不参与查询结果所以高亮显示的内容中不含是依靠ginfo查询出的结果的
                goods.setId(id);
                goods.setGname(gname);
                goods.setGimage(gimage);
                goods.setGprice(gprice);
                goods.setGsave(gsave);
                //进行高亮结果的设置，用高亮结果替换掉普通结果中的对应goods对象，不含高亮的goods对象不变
                // 只对gname字段高亮
                //查询所有ID中当然id的商品所有字段
                if (highlighting.containsKey(id + "")) {
                    Map<String, List<String>> stringListMap = highlighting.get(id + "");
                    //查询该商品所有字段中的gname字段
                    if (stringListMap.containsKey("gname")) {
                        List<String> gNames = stringListMap.get("gname");
                        //获取当前商品gname中的第一个商品名（如果有其他名字的话），没的话获得第一个就是获取唯一一个商品名
                        String gName = gNames.get(0);
                        goods.setGname(gName);
                    }
                }
                goodsList.add(goods);


            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public int addGoods(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",goods.getId());
        document.setField("gname", goods.getGname());
        document.setField("ginfo", goods.getGinfo());
        document.setField("gimage", goods.getGimage());
        //将string值转为float类型
        document.setField("gprice", goods.getGprice().floatValue());
        document.setField("gsave",goods.getGsave());
        System.out.println("squeue队列中的商品信息"+goods.toString());

        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
