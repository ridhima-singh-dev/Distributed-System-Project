package com.clement.config;

import com.alibaba.fastjson.JSONObject;
import com.clement.pojo.RestBean;
import com.clement.service.AuthService;
import com.clement.service.impl.AuthServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Locale;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/22 14:49
 * @Desc
 * @Version 1.0
 */
@Configuration
public class SecurityConfig {
    @Resource
    AuthServiceImpl authService;

    @Resource
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/auth/**","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure)
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onAuthenticationSuccess)

                )
                .exceptionHandling(entry -> entry
                        .authenticationEntryPoint(this::onAuthenticationFailure)
                )
                .csrf(csrf -> csrf.disable()
                )
                .cors(cor -> cor
                        .configurationSource(this.configurationSource())
                )
                .rememberMe(rem->rem
                        .rememberMeParameter("remember")
                        .tokenRepository(persistentTokenRepository())
                        .tokenValiditySeconds(3600*24*3)
                )

                .build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository =new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //only first time
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    //cors
    private CorsConfigurationSource configurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        //frontend
        cors.addAllowedOriginPattern("*");
        //cookie
        cors.setAllowCredentials(true);
        //header
        cors.addAllowedHeader("*");
        cors.addExposedHeader("*");
        //method
        cors.addAllowedMethod("*");
        //source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //register source
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String success;
        if (request.getRequestURL().toString().endsWith("logout")) {
            success = JSONObject.toJSONString(RestBean.succcess("Log Out Success"));
        } else {
            success = JSONObject.toJSONString(RestBean.succcess("Log In Success"));
        }

        response.getWriter().write(success);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        String failureLogin = JSONObject.toJSONString(RestBean.failure(401, "Wrong Username or Password"));
        response.getWriter().write(failureLogin);
    }

}
