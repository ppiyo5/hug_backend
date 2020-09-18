package com.fine.hug_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String title;
    private String version;

    List<ResponseMessage> responseMessages = new ArrayList<>();

    public List<ResponseMessage> getResponseMessages() {
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("Success")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(201)
                .message("Created")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(401)
                .message("Unauthorized")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(403)
                .message("Forbidden")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .build());

        return responseMessages;
    }

    @Bean
    public Docket api() {
        version = "v1";
        title = "Hug Api ";

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) /* 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출 */
                .paths(PathSelectors.ant("/api/**")) /* 그 중 /api/** 인 URL 들만 필터링 */
                .build()
                .apiInfo(apiInfo(title, version))
                .globalResponseMessage(RequestMethod.GET, getResponseMessages());
    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "Hug API Docs",
                version,
                "www.example.com",
                new Contact("Contact Me", "www.example.com", "foo@example.com"),
                "Licenses",

                "www.example.com",

                new ArrayList<>());
    }

}
