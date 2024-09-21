package com.codenear.butterfly.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "CODE NEAR",
                description = "CODE NEAR 나비 API 문서",
                version = "0.0.1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("JWT");
        Components component = new Components().addSecuritySchemes("JWT", new SecurityScheme()
                .name("JWT")
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .description("토큰의 Bearer 제외한 내용 입력")
        );

        return new OpenAPI()
                .components(new Components())
                .addSecurityItem(securityRequirement)
                .components(component);
    }

    @Bean
    public GroupedOpenApi auth() {
        String groupName = "회원가입, 로그인 API";
        String[] paths = new String[]{"/auth/**", "/oauth/**"};

        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi member() {
        String groupName = "유저 API";
        String paths = "/member/**";

        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi product() {
        String groupName = "상품 API";
        String paths = "/products/**";

        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(paths)
                .build();
    }
}
