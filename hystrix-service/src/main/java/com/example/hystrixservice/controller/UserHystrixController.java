package com.example.hystrixservice.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.example.hystrixservice.pojo.Result;
import com.example.hystrixservice.pojo.User;
import com.example.hystrixservice.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Description:
 *
 * @author JourWon
 * @date 2019/12/18 16:52
 */
@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping("/testFallback/{id}")
    public Result testFallback(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/testException/{id}")
    public Result testException(@PathVariable Long id) {
        return userService.getUserException(id);
    }

    @GetMapping("/testCommand/{id}")
    public Result getUserCommand(@PathVariable Long id) {
        return userService.getUserCommand(id);
    }

    @GetMapping("/testCache/{id}")
    public Result testCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return new Result("操作成功", 200);
    }

    @GetMapping("/testRemoveCache/{id}")
    public Result testRemoveCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.removeCache(id);
        userService.getUserCache(id);
        return new Result("操作成功", 200);
    }

    @GetMapping("/testCollapser")
    public Result testCollapser() throws ExecutionException, InterruptedException {
        Future<User> future1 = userService.getUserFuture(1L);
        Future<User> future2 = userService.getUserFuture(2L);
        future1.get();
        future2.get();
        ThreadUtil.safeSleep(200);
        Future<User> future3 = userService.getUserFuture(3L);
        future3.get();
        return new Result("操作成功", 200);
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    @GetMapping("/testFallback1/{id}")
    public Result getUser(@PathVariable Long id) throws Exception {
        throw new Exception();
//        return restTemplate.getForObject(userServiceUrl + "/user/{1}", Result.class, id);
    }

    /**
     * 声明的参数需要包含controller的声明参数
     * @param id
     * @return
     */
    public Result fallbackMethod1(@PathVariable Long id) {
        return new Result("服务调用失败", 500);
    }

}
