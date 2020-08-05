package com.example.feignservice.controller;

import com.example.feignservice.pojo.Result;
import com.example.feignservice.pojo.User;
import com.example.feignservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:添加UserFeignController调用UserService实现服务调用
 *
 * @author JourWon
 * @date 2019/12/20 17:23
 */
@RestController
@RefreshScope
@RequestMapping("/user")
public class UserFeignController {


    @Value("${config.info}")
    private String configInfo;

    @Autowired
    private UserService userService;

    @GetMapping("/config")
    public Result<String> getConfig(){
        return new Result<>(configInfo);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/listUsersByIds")
    public Result<List<User>> listUsersByIds(@RequestParam List<Long> ids) {
        return userService.listUsersByIds(ids);
    }

    @GetMapping("/getByUsername")
    public Result<User> getByUsername(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return userService.delete(id);
    }

}
