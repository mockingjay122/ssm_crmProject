package com.gj.crm.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gj.crm.commons.contants.Contants;
import com.gj.crm.commons.domian.ReturnObject;
import com.gj.crm.commons.utils.DataUtils;
import com.gj.crm.commons.utils.StringUtils;
import com.gj.crm.commons.utils.UUIDUtils;
import com.gj.crm.settings.entity.DicValue;
import com.gj.crm.settings.entity.User;
import com.gj.crm.settings.service.DicValueService;
import com.gj.crm.settings.service.userService;
import com.gj.crm.workbench.entity.Activity;
import com.gj.crm.workbench.entity.Clue;
import com.gj.crm.workbench.entity.ClueActivityRelation;
import com.gj.crm.workbench.entity.ClueRemark;
import com.gj.crm.workbench.service.ActivityService;
import com.gj.crm.workbench.service.ClueActivityRelationService;
import com.gj.crm.workbench.service.ClueRemarkService;
import com.gj.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author 郭嘉
 * @date 2022/10/18 - 17:07
 */
@Controller
public class ClueController {
    @Autowired
    private userService  userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(Model model){
        List<User> userList=userService.queryAllUsers();
        List<DicValue> appellationList=dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList=dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");

        PageHelper.startPage(Contants.INIT_PAGE_NO, Contants.INIT_PAGE_SIZE);
        Map<String,Object> map=new HashMap<>();
        map.put("fullname",Contants.NULL_VALUE);
        map.put("company",Contants.NULL_VALUE);
        map.put("phone",Contants.NULL_VALUE);
        map.put("source",Contants.NULL_VALUE);
        map.put("mphone",Contants.NULL_VALUE);
        map.put("state",Contants.NULL_VALUE);
        map.put("owner",Contants.NULL_VALUE);
        List<Clue> clueList=clueService.queryClueByCondition(map);
        PageInfo<Clue> pageInfo=new PageInfo<>(clueList,5);

        model.addAttribute("userList",userList);
        model.addAttribute("appellationList",appellationList);
        model.addAttribute("clueStateList",clueStateList);
        model.addAttribute("sourceList",sourceList);
        model.addAttribute("page",pageInfo);
        //请求转发
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveClue.do")
    public @ResponseBody Object saveClue(Clue clue, Model model, HttpSession session){
        //封装参数
        User user=(User)session.getAttribute(Contants.SSESSION_USER_KEY);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DataUtils.formateDataTime(new Date()));
        clue.setCreateBy(user.getId());
        ReturnObject returnObject=new ReturnObject();
        //调用service
        try {
            int res=clueService.saveCreateClue(clue);
            if(res>0){
                PageHelper.startPage(Contants.INIT_PAGE_NO, Contants.INIT_PAGE_SIZE);
                Map<String,Object> map=new HashMap<>();
                map.put("fullname",Contants.NULL_VALUE);
                map.put("company",Contants.NULL_VALUE);
                map.put("phone",Contants.NULL_VALUE);
                map.put("source",Contants.NULL_VALUE);
                map.put("mphone",Contants.NULL_VALUE);
                map.put("state",Contants.NULL_VALUE);
                map.put("owner",Contants.NULL_VALUE);
                List<Clue> clueList=clueService.queryClueByCondition(map);
                PageInfo<Clue> pageInfo=new PageInfo<>(clueList,5);
                model.addAttribute("page",pageInfo);
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }

        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }

 /*   @RequestMapping("/workbench/clue/queryClueByCondition.do")
    public String queryClueByCondition(String fullname,String company,String phone,String source,String owner,String mphone,String state,Model model){
        PageHelper.startPage(Contants.INIT_PAGE_NO, Contants.INIT_PAGE_SIZE);
        Map<String,Object> map=new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("source",source);
        map.put("mphone",mphone);
        map.put("state",state);
        map.put("owner",owner);
        List<Clue> clueList=clueService.queryClueByCondition(map);
        PageInfo<Clue> pageInfo=new PageInfo<>(clueList,5);
        model.addAttribute("page",pageInfo);
        ReturnObject returnObject=new ReturnObject();
        return "workbench/clue/index";
    }*/
    @RequestMapping("/workbench/clue/detailClue.do/{id}")
    public String detailClue(@PathVariable("id") String id,Model model){
        //掉用service方法查询数据
        Clue clue=clueService.queryClueDetailById(id);
        List<ClueRemark> clueRemarkList=clueRemarkService.queryClueRemarkForDetail(id);
        List<Activity> activityList=activityService.queryActivityForDetailByClueId(id);
        model.addAttribute("clue",clue);
        model.addAttribute("clueRemarkList",clueRemarkList);
        model.addAttribute("activityList",activityList);
        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/queryActivityByNameClueId.do")
    public @ResponseBody Object queryActivityByNameClueId(String activityName,String clueId){
        // 封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        // 调用service
       List<Activity> activityList= activityService.queryActivityByNameClueId(map);
        /*ReturnObject returnObject=new ReturnObject();
        returnObject.setReturnData(activityList);*/
       return activityList;
    }

    @RequestMapping("/workbench/clue/saveBund.do")
    public @ResponseBody Object saveBund(String[] activityIds,String clueId,Model model){
        //封装参数
        String[] finalId= StringUtils.buildIds(activityIds[0]);
        ClueActivityRelation car=null;
        List<ClueActivityRelation> carList=new ArrayList<>();
        ReturnObject returnObject=new ReturnObject();
        for(String id:finalId){
            car=new ClueActivityRelation();
            car.setId(UUIDUtils.getUUID());
            car.setActivityId(id);
            car.setClueId(clueId);
            carList.add(car);
        }
        try {
            int res=clueActivityRelationService.saveClueActivityRelationByList(carList);
            if(res>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList=activityService.queryActivityForDetailByClueId(clueId);
                model.addAttribute("activityList",activityList);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("请稍后重试");
            }
        }catch (Exception e){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("请稍后重试");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/saveUnBund.do")
    public @ResponseBody Object saveUnBund(String activityId,String clueId,Model model){
        ClueActivityRelation car=new ClueActivityRelation();
        ReturnObject returnObject=new ReturnObject();
        try {
            car.setId(UUIDUtils.getUUID());
            car.setActivityId(activityId);
            car.setClueId(clueId);
            int res=clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(car);
            if(res>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList=activityService.queryActivityForDetailByClueId(clueId);
                model.addAttribute("activityList",activityList);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/toConvert.do/{id}")
    public String toConvert(@PathVariable("id") String id,Model model){
        // 调用service层方法，查询线索的明细信息
        Clue clue=clueService.queryClueDetailById(id);
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        model.addAttribute("clue",clue);
        model.addAttribute("stageList",stageList);
        return "workbench/clue/convert";
    }


    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    public @ResponseBody Object queryActivityForConvertByNameClueId(String activityName,String clueId){
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList=activityService.queryActivityForConvertByNameClueId(map);
        return activityList;
    }
    @RequestMapping("/workbench/clue/convertClue.do")
    public @ResponseBody Object convertClue(String clueId,String money,String name,String expectedDate,String stage,String activityId,String isCreate,HttpSession session){

        //疯转参数
        User user=(User) session.getAttribute(Contants.SSESSION_USER_KEY);
        Map<String ,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreate",isCreate);
        map.put(Contants.SSESSION_USER_KEY,user);
        ReturnObject returnObject=new ReturnObject();
        try {
           clueService.saveConvert(map);
           returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("转换失败，请稍后重试");
            e.printStackTrace();
        }
        return  returnObject;
    }
}
