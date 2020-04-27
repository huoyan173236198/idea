package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.entity.GoodsType;
import com.cn.service.IGoodTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/22 12:27
 */
@Api(description = "商品类型接口")
@Controller
@RequestMapping("/gtype")
public class GoodsTypeConreoller {

    public static final Logger log = LoggerFactory.getLogger(GoodsTypeConreoller.class);

    @Reference
    private IGoodTypeService goodTypeService;

    @ApiOperation("商品类型列表")
    @GetMapping("/list")
    public String goodsTypeList(Model model){
        log.info("调用商品类型列表接口");
        goodTypeService.goodsTypeList();
        return "gtypelist";
    }

    @ApiOperation("商品类型添加接口")
    @PostMapping("/insert")
    public String goodsTypeInsert(GoodsType goodsType){
        log.info("调用商品类型添加接口");
        goodTypeService.goodsTypeInsert(goodsType);
        return "redirect:/gtype/list";

    }

    @ApiOperation("商品类型删除接口")
    @PostMapping("/delete")
    public String goodsTypeDelete(GoodsType goodsType){
        log.info("调用商品类型删除接口");
        goodTypeService.goodsTypeDelete(goodsType);
        return "redirect:/gtype/list";
    }

    @ApiOperation("修改类型前调用的查询类型接口")
    @GetMapping("/queryAllById")
    public String queryAllById(Integer id){
        log.info("获取当前类型的状态");
        return null;
    }

    @ApiOperation("商品类型修改接口")
    @PostMapping("/update")
    public String goodsTypeUpdate(GoodsType goodsType){
        log.info("调用商品类型修改接口");
        goodTypeService.goodTypeUpdate(goodsType);
        return null;
    }

    //该方法是修改商品所具备的类型前调用的方法
    @ApiOperation("获取当前商品类型ID")
    @GetMapping("/listajax")
    @ResponseBody
    public List<GoodsType> listAjax(Integer gid){
        log.info("根据gid获取当前商品类型ID");
        List<GoodsType> goodsTypes = goodTypeService.listAjax(gid);
        return goodsTypes;
    }
}
