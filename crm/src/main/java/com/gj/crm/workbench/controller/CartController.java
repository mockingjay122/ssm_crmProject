package com.gj.crm.workbench.controller;

import com.gj.crm.workbench.entity.FinalAo;
import com.gj.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/24 - 18:41
 */
@Controller
public class CartController {

    @Autowired
    private TranService tranService;

    @RequestMapping("/workbench/chart/transaction/index.do")
    public String index(){


        //跳转页面
        return "workbench/chart/transaction/index";
    }
    @RequestMapping("/workbench/chart/transaction/queryCountOfTranByGroup.do")
    public @ResponseBody Object queryCountOfTranByGroup(){

        List<FinalAo> finalAoList=tranService.queryCountOfTranByGroup();
        //跳转页面
        return finalAoList;
    }
}
