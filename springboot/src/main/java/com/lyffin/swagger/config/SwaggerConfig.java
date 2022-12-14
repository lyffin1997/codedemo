package com.lyffin.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {
//可以多实例设置分组
//    @Bean
//    public Docket docket1() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("demo1");
//    }
//
//    @Bean
//    public Docket docket2() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("demo2");
//    }
//
    @Bean
    public Docket docket3() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("demo3");
    }

    //配置swagger docket bean实例
    @Bean
    public Docket docket(Environment environment) {
        //设置要显示swagger的环境，这里指配置文件中的dev环境和test环境
        Profiles profiles = Profiles.of("swagger");
        //获取项目的环境,判断当前环境是否为自己设定的环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //设置分组
                .groupName("demo")
                //是否开启swagger，默认为true
                .enable(flag)
                .select()
                //RequestHandlerSelectors，配置扫描接口的方式
                //basePackage:指定扫描接口的包
                //.apis(RequestHandlerSelectors.any()) 扫描所有接口
                //.apis(RequestHandlerSelectors.none()) 不扫描接口
                //withClassAnnotation: 扫描类上的注解
                //withMethodAnnotation: 扫描方法上的注解，参数为注解的类
                .apis(RequestHandlerSelectors.basePackage("com.lyffin.codedemo.swagger.controller"))
                //过滤路径，如下只扫描/lyffin/**的接口
                //.paths(PathSelectors.ant("/lyffin/**"))
                .build();
    }

    //配置swagger信息
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("黎峰", "https://lyffin1997.github.io/", "229114260@qq.com");
        return new ApiInfo(
                "lyffin's Swagger Api",
                "学习swagger",
                "1.0",
                "https://lyffin1997.github.io/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
