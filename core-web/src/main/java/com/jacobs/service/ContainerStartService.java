package com.jacobs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ContainerStartService implements InitializingBean {

    @Autowired
    DemoService demoService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet init, call demoService {}", demoService.getUserNameForTest());
    }

    @PostConstruct
    public void init() {
        log.info("PostConstruct init, call demoService {}", demoService.getUserNameForTest());
    }
}
