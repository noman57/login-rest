package com.ufril.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Noman
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket xhopperApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ufril-rest-api")
                .select()
                .paths(paths())
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(newArrayList(basicAuth()))
                .securityContexts(newArrayList(securityContext()));
    }



    private BasicAuth basicAuth() {
        return new BasicAuth("myBasic");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        //AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope authorizationScope = new AuthorizationScope("read", "read access");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("myBasic", authorizationScopes));
    }



    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Ufril REST API")
                .description("Ufril API is for online shopping and home delevary management")
                .termsOfServiceUrl("http://ufril.com/terms-of-service")
                .contact("info@ufril.com")
                .version("v1")
                .license("License")
                .licenseUrl("http://ufril.com/lincenses")
                .build();
    }


    private Predicate<String> paths() {
        return or(regex("/v1/*.*"));
    }
}
