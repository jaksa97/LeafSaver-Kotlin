package com.github.jaksa97.LeafSaver_Kotlin.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("LeafSaver-Kotlin API")
                    .description("API documentation for LeafSaver-Kotlin")
                    .version("1.0.0")
            )
    }
}