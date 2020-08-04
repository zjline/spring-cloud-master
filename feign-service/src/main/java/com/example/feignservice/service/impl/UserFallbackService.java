package com.example.feignservice.service.impl;

import com.example.feignservice.pojo.Result;
import com.example.feignservice.pojo.User;
import com.example.feignservice.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 *
 * @author JourWon
 * @date 2019/12/20 18:28
 */
@Component
public class UserFallbackService implements UserService {

    @Override
    public Result insert(User user) {
        return new Result("调用失败，服务被降级",500);
    }

    @Override
    public Result<User> getUser(Long id) {
        return new Result("调用失败，服务被降级",500);
    }

    @Override
    public Result<List<User>> listUsersByIds(List<Long> ids) {
        return new Result("调用失败，服务被降级",500);
    }

    @Override
    public Result<User> getByUsername(String username) {
        return new Result("调用失败，服务被降级",500);
    }

    @Override
    public Result update(User user) {
        return new Result("调用失败，服务被降级",500);
    }

    @Override
    public Result delete(Long id) {
        return new Result("调用失败，服务被降级",500);
    }

}
