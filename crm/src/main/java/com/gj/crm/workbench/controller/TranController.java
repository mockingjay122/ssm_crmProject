package com.gj.crm.workbench.controller;

import com.gj.crm.commons.contants.Contants;
import com.gj.crm.commons.domian.ReturnObject;
import com.gj.crm.settings.entity.DicValue;
import com.gj.crm.settings.entity.User;
import com.gj.crm.settings.mapper.DicValueMapper;
import com.gj.crm.settings.service.DicValueService;
import com.gj.crm.settings.service.userService;
import com.gj.crm.workbench.entity.Tran;
import com.gj.crm.workbench.entity.TranHistory;
import com.gj.crm.workbench.entity.TranRemark;
import com.gj.crm.workbench.mapper.TranMapper;
import com.gj.crm.workbench.service.CustomerService;
import com.gj.crm.workbench.service.TranHistoryService;
import com.gj.crm.workbench.service.TranRemarkService;
import com.gj.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 15:29
 */
@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private userService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TranService tranService;

    @Autowired
    private TranHistoryService tranHistoryService;

    @Autowired
    private TranRemarkService tranRemarkService;

    @RequestMapping ("/workbench/tran/index.do")
    public String index(Model model){
        List<DicValue> tranList =dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        model.addAttribute("tranList",tranList);
        model.addAttribute("sourceList",sourceList);
        model.addAttribute("stageList",stageList);
        return "workbench/transaction/index";
    }

    @RequestMapping ("/workbench/tran/toSave.do")
    public String toSave(Model model){
        //调用service
        List<User> userList=userService.queryAllUsers();
        List<DicValue> tranList =dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        model.addAttribute("tranList",tranList);
        model.addAttribute("sourceList",sourceList);
        model.addAttribute("stageList",stageList);
        model.addAttribute("userList",userList);

        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/tran/getPossibilityByStage.do")
    public  @ResponseBody  Object getPossibilityByStage(String stageValue){
        //解析配置文件获取阶段可能性
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(stageValue);
        System.out.println(possibility);
        //返回响应信息
        return possibility;
    }

    @RequestMapping("/workbench/tran/queryAllCustomerName.do")
    public @ResponseBody Object queryAllCustomerName(String customerName){
        List<String> customerNameList=customerService.queryAllCustomerName(customerName);
        return customerNameList;
    }
    //保存创建交易
    @RequestMapping("/workbench/tran/saveCreateTran.do")
    public @ResponseBody Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
            //封装参数
        map.put(Contants.SSESSION_USER_KEY,session.getAttribute(Contants.SSESSION_USER_KEY));
        //保存数据
        ReturnObject returnObject=new ReturnObject();
        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重置");
        }
        return returnObject;
    }
    //交易明细
    @RequestMapping("/workbench/tran/detailTran.do/{id}")
    public String detailTran(@PathVariable("id") String tranId,Model model){
        Tran tran=tranService.queryTranForDetailById(tranId);
        List<TranRemark> tranRemarkList=tranRemarkService.queryTranRrmarkForDetailById(tranId);
        List<TranHistory> tranHistoryList=tranHistoryService.queryTranHistoryForDetailById(tranId);
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(tran.getStage());


        model.addAttribute("tran",tran);
        model.addAttribute("tranRemarkList",tranRemarkList);
        model.addAttribute("tranHistoryList",tranHistoryList);
        model.addAttribute("possibility",possibility);
        model.addAttribute("stageList",stageList);

        return "workbench/transaction/detail";
    }
}
