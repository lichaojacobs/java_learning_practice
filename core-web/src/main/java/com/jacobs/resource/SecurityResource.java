package com.jacobs.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacobs.exception.CommonException;
import com.jacobs.exception.CommonRestException;
import com.jacobs.module.User;
import com.jacobs.service.DemoService;

/**
 * 测试接口
 */
@RestController
public class SecurityResource implements ApplicationContextAware {

    @Autowired
    DemoService demoService;

    @RequestMapping("/test")
    public void putCache() {
        try {
            User user = new User();
            user.setFirstName("lichao");
            demoService.insertUserName(user);
        } catch (Exception ex) {
            throw new CommonRestException(CommonException.PARAMETER_ERROR);
        }
    }

    @RequestMapping("/test2")
    public void testCache() {
        String name = demoService.getUserNameFromCache();
        if (name != null) {
            System.out.println("缓存为:" + name);
        }
    }

    @RequestMapping("/403")
    public String apiFor403() {
        return "403";
    }


    /**
     * 用户列表
     *
     * @param page     第几页
     * @param pageSize 页数
     * @return
     */
    @GetMapping(value = "/test3", produces = "application/json")
    public List<User> getMessage(@RequestParam(value = "page", required = true) Integer page, int pageSize) {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setFirstName("li");
        user1.setLastName("chao");
        user1.setId(1l);
        users.add(user1);
        User user2 = new User();
        user2.setFirstName("wang");
        user2.setLastName("lu");
        user2.setId(2l);
        users.add(user2);
        return users;
    }

    ApplicationContext applicationContext;

//    @GetMapping(value = "/login_auto", produces = {"application/json"})
//    public String login(
//            HttpServletRequest request) {
//        AuthenticationManager authManager = applicationContext.getBean(AuthenticationManager.class);
//        Authentication authentication = authManager
//                .authenticate(new UsernamePasswordAuthenticationToken("lichao", "123456"));
//
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);
//        // Create a new session and add the security context.
//        //这里要注意更新session
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//
//        return "success";
//    }

    @RequestMapping(value = "/login", produces = {"application/json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {

        return "login success";
    }

    @RequestMapping(value = "/login_success", produces = {"application/json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginSuccess() {

        return "login success";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
