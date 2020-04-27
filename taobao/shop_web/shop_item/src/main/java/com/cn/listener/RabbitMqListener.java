package com.cn.listener;

import com.cn.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/11 22:30
 */
@Component
public class RabbitMqListener {
    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void msgHander(Goods goods){
        Template template = null;
        try {
            template = configuration.getTemplate("goodsitem.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        map.put("images", goods.getGimage().split("\\|"));
        map.put("contextPath", "");
        String path = RabbitMqListener.class.getResource("/static").getPath().replace("20%", " ");
        System.out.println("获得的路径为"+path);

        File file = new File(path + "/page");
        if (!file.exists()) {
            file.mkdirs();
        }

        try (
            //将流放入括号中可以省下关流的步骤
            Writer writer = new FileWriter(file.getAbsolutePath() +"/"+ goods.getId() + ".html")
        ){
            template.process(map,writer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

}
