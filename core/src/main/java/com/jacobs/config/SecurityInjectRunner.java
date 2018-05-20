package com.jacobs.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author lichao
 * @date 2018/05/13
 */
@Component
public class SecurityInjectRunner implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AuthenticationManager authManager = applicationContext.getBean(AuthenticationManager.class);
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken("lichao", "123456"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
