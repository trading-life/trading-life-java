package com.aiden.trading.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    private static final String X_ACCESS_TOKEN = "sa_token";

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("wlddhj@163.com");
        contact.setName("huangjian");
        contact.setUrl("http://doc.xiaominfo.com");
        return new OpenAPI()
                // 增加swagger授权请求头配置
                .components(new Components().addSecuritySchemes(X_ACCESS_TOKEN,
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(X_ACCESS_TOKEN)))
                .info(new Info()
                        .title("Shi9 后台服务API接口文档")
                        .version("1.0")
                        .contact(contact)
                        .description("Knife4j集成springdoc-openapi示例")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-public")
//                .pathsToMatch("/public/**")
//                .build();
//    }
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-admin")
//                .pathsToMatch("/admin/**")
//                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class))
//                .build();
//    }

}