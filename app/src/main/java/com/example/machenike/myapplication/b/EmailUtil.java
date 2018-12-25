package com.example.machenike.myapplication.b;

import android.content.Context;

import com.zhy.autolayout.utils.L;

public class EmailUtil {


    public static void autoSendMail(Context context, String msuggestions) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.sina.com");//smtp地址
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("ts1201_1201@sina.com");//邮箱名字
        mailInfo.setPassword("aa123123");// 邮箱密码
        mailInfo.setFromAddress("ts1201_1201@sina.com");// 发送方邮件地址
        mailInfo.setToAddress("3312885903@qq.com");//接受方邮件地址
        mailInfo.setSubject("subject");
        mailInfo.setContent(msuggestions);
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);// 发送文体格式
        L.e(msuggestions);
        // sms.sendHtmlMail(mailInfo);// 发送html格式

//        邮件的发送方：新浪邮箱
//        ts1201_1201@sina.com
//                aa123123
    }

}
