package com.wyfx;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wyfx.ft.repository.DataRepo;
import com.wyfx.ft.repository.FileRepo;
import com.wyfx.hessian.service.WyfxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class WyfxApplication extends WebMvcConfigurerAdapter {
    @Autowired
    private DataRepo dataRepo;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private WyfxService wyfxService;

    public static void main(String[] args) {

        SpringApplication.run(WyfxApplication.class, args
        );
    }

    @Bean(name = "/WyfxService")
    public HessianServiceExporter exportWyfxService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(wyfxService);
        exporter.setServiceInterface(WyfxService.class);
//        exporter.
        return exporter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);


//		 * 1、需要先定义一个 convert 转换消息的对象;
//		 * 2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
//		 * 3、在convert中添加配置信息.
//		 * 4、将convert添加到converters当中.
//		 *


        // 1、需要先定义一个 convert 转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );

        //3、在convert中添加配置信息.
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //4、将convert添加到converters当中.
        converters.add(fastConverter);

    }

    @PostConstruct
    public void construct() {
        System.err.println("init");
        //new TimerTask().run();
        //new Thread(new Server(fileRepo,dataRepo)).start();
        //WyfxContext.saveUsername("admin");
    }
}
