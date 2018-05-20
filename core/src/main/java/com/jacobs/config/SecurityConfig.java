package com.jacobs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author lichao
 * @date 2018/05/13
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//禁用csrf
            .authorizeRequests()
            .antMatchers("/users/**").hasRole("USER")   // 需要相应的角色才能访问
//            .antMatchers("/admins/**").hasRole("ADMIN")   // 需要相应的角色才能访问
            //.antMatchers("/login").permitAll() //这里不能这样，因为formlogin已经要求/login链接要做登录拦截了，会冲突
            .and()
            .formLogin()
            .loginPage("/login").successForwardUrl("/login_success")
            .and()
            .exceptionHandling().accessDeniedPage("/403");
//            .and()
//            .exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝访问就重定向到 403 页面
    }

    /**
     * 用户信息服务
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 在内存中存放用户信息
        manager.createUser(User.withUsername("lichao")
                               .password("123456")
                               .roles("USER", "ADMIN", "100010_ADMIN")
                               .build());
        return manager;
    }

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }
}
