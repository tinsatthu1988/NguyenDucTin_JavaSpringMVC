package com.aptech.book.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new BookStoreUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**").hasAuthority("Admin")

                .antMatchers("/categories/**")
                .hasAnyAuthority("Admin","Editor")

                .antMatchers("/books/","/books")
                .hasAnyAuthority("Admin","Editor","Shipper","Salesperson","Assistant")

                .antMatchers("/books/new", "/books/edit", "/books/delete")
                .hasAnyAuthority("Admin","Editor")

                .antMatchers("/users","/categories","/books").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/index", true)
                    .usernameParameter("email")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                .rememberMe()
                .key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
                .tokenValiditySeconds(14 * 24 * 60 * 60)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**","/css/**");
    }
}
