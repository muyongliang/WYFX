package com.wyfx.upms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Author: liuxingyu
 * DATE: 2017-07-03.10:07
 * description:加载配置文件
 * version:
 */
@Configuration
@ImportResource(locations = {"classpath:spring-shiro.xml", "classpath:dispatcher-servlet.xml"})
public class ConfigClass {
}
