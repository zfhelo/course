package org.gdpi.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zhf
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationSuccessHandler successHandler;
    @Resource
    private AuthenticationFailureHandler failureHandler;
    @Resource
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permitPath = {
                "/login.html", "/register.html", "/forget.html", "/css/**", "/plugins/**","/favicon.ico",
                "/js/**", "/img/**","/error/**",
                "/public/**"
        };
        http.authorizeRequests()
                .antMatchers(permitPath)
                .permitAll()
                .antMatchers("/tea/**").hasRole("TEA")
                .antMatchers("/stu/**").hasRole("STU")
                .antMatchers("/admin/**").hasRole("TEA")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin() // 设置登录页面
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                //.and()
                //.rememberMe()
                //.tokenValiditySeconds(3600*24)
                //.tokenRepository(persistentTokenRepository())
                .and()
                .exceptionHandling().accessDeniedPage("/error/403.html")
                .and()
                .headers().frameOptions().disable() // 关闭 x-frame-options
                .and()
                .csrf()
                .disable(); // CSRF跨站请求伪造关闭
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setCreateTableOnStartup(false);
        repository.setDataSource(dataSource);
        return repository;
    }

}
