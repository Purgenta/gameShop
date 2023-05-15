package com.purgenta.gameshop.config;


import com.purgenta.gameshop.authentication.JwtAuthenticationFilter;
import com.purgenta.gameshop.exceptionhandlers.AccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private static final String[] whiteList =
            {
                    "/cart/items",
                    "/authentication/**",
                    "/v3/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui/**","/files/**", "/photos/products/**","/games/filterValues",
                    "/games/getGames",
            };

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(whiteList).permitAll()
                .requestMatchers("/admin/**", "/orders/**,/user/{id},/publishers/addPublisher").hasRole("ADMIN")
                .requestMatchers("/productManagement/**").hasAnyRole("ADMIN", "CONTENT_MANAGER")
                .anyRequest()
                .authenticated().and()
                .exceptionHandling().authenticationEntryPoint(new AccessDeniedHandler())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
