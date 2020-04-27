package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.entity.Goods;
import com.cn.service.IGoodsService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/21 16:29
 */
@Api(description = "商品信息接口")
@Controller
@RequestMapping("/goods")
public class GoodsController {

    public static final Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Reference
    private IGoodsService goodsService;

    @GetMapping("/list")
    @ApiOperation("商品信息列表")
    public String goodsList(Model model) {
        log.info("查询商品列表");
        List<Goods> goods = goodsService.goodsList();
        model.addAttribute("goods", goods);
        return "goodslist";
    }

    //在调用该方法前需要先去GoodsTypeController用ajax调用/gtype/list获取商品类型后带着tid调用该方法
    @ApiOperation("添加商品")
    @PostMapping("/insert")
    public String goodsInsert(Goods goods) {
        log.info("添加商品");
        goodsService.goodsInsert(goods);
        return "redirect:/goods/list";
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    public String goodsDelete(Goods goods) {
        log.info("删除商品");
        return "redirect:/goods/list";
    }

    //调用该方法前需要先调用GoodsTypeController的/gtype/listajax通过tid查出来是否拥有该类型
    //每个商品只有一个类型所以不需要中间表
    @ApiOperation("修改商品")
    @PostMapping("/update")
    public String goodsUpdate(Goods goods){
        log.info("修改商品");
        return "redirect:/goods/list";
    }

    @ApiOperation("获取多媒体文件")
    @PostMapping("/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile file){
        log.info("调用上传图片接口");

        String uploadFile = "";
        //截取原图片文件后缀也就是图片类型
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index + 1);
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    //获取文件的输入流
                    file.getInputStream(),
                    //获取文件大小
                    file.getSize(),
                    //不包含.的后缀
                    suffix,
                    null
            );

            uploadFile = storePath.getFullPath();
        } catch (IOException e) {
            log.error("多媒体文件异常："+e.getMessage(),e);
        }
        //这步仅仅是选择了图片并且上传但是还没有添加商品
        //这里必须写成json格式，不然返回回去webupload无法识别
        //由于商品没有添加所以现在没有商品ID，将该商品的图片路径返回回去放在隐藏域中和添加商品的表单一起提交到数据库中就可以查
        return "{\"filepath\":\"" + uploadFile + "\"}";
    }

}
