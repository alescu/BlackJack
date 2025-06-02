package cat.itacademy.s05.t01.n01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Deshabilita CSRF (normalment necessari per APIs REST sense sessions de navegador)
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll() // Permet l'accés a TOTS els endpoints sense autenticació
                );
        return http.build();
    }
}
