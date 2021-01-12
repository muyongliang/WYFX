package com.wyfx.cs.mail;

/**
 * Created by liu on 2017/12/22.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MailSender mailSender = new MailSender("liuxingyu_sc@163.com", "lxy117837");
        mailSender.send("1019925684@qq.com", "测试", "naolddsfl");
    }
}
