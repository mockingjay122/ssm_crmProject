package com.gj.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 郭嘉
 * @date 2022/9/29 - 19:13
 */
@Controller
public class mainController {


    @RequestMapping("/workbench/main/index.do")
    public String index(){
        //跳转到workben/index.html
         return "workbench/main/index";
    }
}
