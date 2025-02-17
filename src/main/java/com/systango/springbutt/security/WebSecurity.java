package com.systango.springbutt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.systango.springbutt.security.SecurityConstants.SIGN_UP_URL;


/**
 * Created by Arpit Khandelwal.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .cors()
                .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    //Swagger exceptions
                    .antMatchers(HttpMethod.GET, "/v2/api-docs", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/swagge‌​r-ui.html")
                    .permitAll()
                    //Static resources exception
                    .antMatchers(HttpMethod.GET, "/", "/resources/static/**", "/css/**", "/js/**", "/img/**", "/favicon.ico")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/manage/**")
                    .permitAll()
                    //Sign-up API exception
                    .antMatchers(HttpMethod.POST, SIGN_UP_URL)
                    .permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
					.loginPage("/login")
					.permitAll()
                .and()
                .logout()
					.permitAll()
					.and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //@formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}