package com.desklamp.gateway.security;

import com.alibaba.fastjson.JSONObject;
import com.desklamp.gateway.security.jwt.JWTAuthenticationEntryPoint;
import com.desklamp.gateway.security.jwt.JWTAuthenticationFilter;
import com.desklamp.gateway.security.jwt.JWTLoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 无状态登录及访问安全配置
 * @author by Joney on 2019/3/26 22:06
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled from client_account where username = ?")
                .authoritiesByUsernameQuery("select username, authorityName from client_relations where username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/**").authenticated()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 设置认证失败处理
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    public JWTLoginFilter loginFilter() throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager());
        loginFilter.setSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
            log.info(">>> 用户凭据验证通过：{}", JSONObject.toJSONString(authentication.getPrincipal()));
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().write(JSONObject.toJSONString(new HashMap<String, Object>() {{
                put("status", HttpStatus.OK.value());
                put("message", "验证通过");
            }}));
        });
        loginFilter.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
            log.error(">>> 用户凭据验证异常：{}", e.getMessage());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().write(JSONObject.toJSONString(new HashMap<String, Object>() {{
                put("status", HttpStatus.UNAUTHORIZED.value());
                put("message", "用户凭据验证无效");
            }}));
        });
        return loginFilter;
    }

    @Bean
    public JWTAuthenticationEntryPoint authenticationEntryPoint() {
        return new JWTAuthenticationEntryPoint();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
