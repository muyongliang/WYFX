package com.wyfx.ft.utils;

import com.wyfx.ft.repository.DataRepo;
import com.wyfx.ft.repository.FileRepo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server implements Runnable, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired
    private DataRepo dataRepo;
    @Autowired
    private FileRepo fileRepo;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Server.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Server bean = applicationContext.getBean(Server.class);
        new Thread(bean).start();
    }

    @Override
    public void run() {
        PrintWriter os = null;
        Socket sk = null;
        try {
            ServerSocket ss = new ServerSocket(9000);
            System.out.println("等待连接....");
            while (true) {
                sk = ss.accept();
                new Thread(new Sk(fileRepo, dataRepo, sk)).start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }


}
