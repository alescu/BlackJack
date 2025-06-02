package cat.itacademy.s05.t01.n01.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API S05T01N01 - BlackJack")
                        .version("0.0.1")
                        .description("Documentació de l'API principal del projecte S05T01N01 (BlackJack)."));
    }

    @Bean
    public GroupedOpenApi blackjackApi() {
        return GroupedOpenApi.builder()
                .group("BlackJack-api")
                .pathsToMatch("/**")
                .build();
    }
}