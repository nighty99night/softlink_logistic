package ru.kalinichenko.softlink_logistic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kalinichenko.softlink_logistic.security.LoginSuccessHandler;
import ru.kalinichenko.softlink_logistic.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r.requestMatchers("auth/**", "home/**", "/img/main/**", "/shipping/**", "/simple", "/templates")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(l -> l
                        .loginPage("auth/login")
                        .defaultSuccessUrl("/")
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                )
                .logout(l -> l
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)
//                        .logoutSuccessHandler(((request, response, authentication) -> request.logout()))
                )

                .userDetailsService(userDetailsService)

        ;

        return httpSecurity.build();
    }


}
