package com.cn.listenter;

import com.cn.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/20 23:11
 */
@Component
public class RabbitMQListenter {
    /**
     *创建线程池来消费队列中的消息
     */
    private ThreadPoolExecutor executor=new ThreadPoolExecutor(10,
            20,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10));


    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String formEmail;

    @RabbitListener(queues = "email_queue")
    public void handler(Email email) {
        //预启动所有核心线程
        executor.prestartAllCoreThreads();
        executor.execute(()->{
            //创建邮件对象
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            //创建spring提供邮箱发送帮助的对象
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            try {
                //设置邮件主题
                mimeMessageHelper.setSubject(email.getSubject());
                //设置邮件发送方
                mimeMessageHelper.setFrom(formEmail);
                //设置邮件接收方
                mimeMessageHelper.setTo(email.getTo());
                //设置邮件内容
                mimeMessageHelper.setText(email.getContent(), true);
                //设置邮件日期
                mimeMessageHelper.setSentDate(new Date());
                //发送邮件
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
