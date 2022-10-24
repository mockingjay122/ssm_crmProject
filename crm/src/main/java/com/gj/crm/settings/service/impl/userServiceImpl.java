package com.gj.crm.settings.service.impl;

import com.gj.crm.settings.entity.User;
import com.gj.crm.settings.mapper.UserMapper;
import com.gj.crm.settings.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/9/16 - 19:25
 */
@Service("userService")
public class userServiceImpl implements userService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        return userMapper.selectByLoginActAndPwd(map);
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.seletAllUser();
    }
}
