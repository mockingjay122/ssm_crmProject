package com.gj.crm.settings.service;

import com.gj.crm.settings.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/9/16 - 19:24
 */
public interface userService {
    User queryUserByLoginActAndPwd(Map<String,Object> map);

    List<User> queryAllUsers();
}
