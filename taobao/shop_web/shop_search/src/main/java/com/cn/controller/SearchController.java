package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.entity.Goods;
import com.cn.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/27 10:16
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("/searchByKey")
    public String searchByKey(String keyword, Model model) {
        System.out.println("获得的关键字" + keyword);
        List<Goods> goodsList = searchService.searchByKey(keyword);
        model.addAttribute("goodsList", goodsList);
        return "searchlist";
    }
}
