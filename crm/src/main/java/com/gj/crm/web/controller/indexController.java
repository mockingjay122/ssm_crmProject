package com.gj.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 郭嘉
 * @date 2022/9/13 - 17:24
 */
@Controller
public class indexController {


    /**
     *  /:  http://127.0.0.0:8080/crm/
     * @return
     */
    @RequestMapping("/")
    public String index(){

        return "index";

    }
}
