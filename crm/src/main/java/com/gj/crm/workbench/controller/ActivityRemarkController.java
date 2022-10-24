package com.gj.crm.workbench.controller;

import com.gj.crm.commons.contants.Contants;
import com.gj.crm.commons.utils.DataUtils;
import com.gj.crm.commons.utils.UUIDUtils;
import com.gj.crm.settings.entity.User;
import com.gj.crm.workbench.entity.Activity;
import com.gj.crm.workbench.entity.ActivityRemark;
import com.gj.crm.workbench.service.ActivityRemarkService;
import com.gj.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/17 - 16:14
 */
@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public String saveCreateActivityRemark(String content, String id, HttpSession session,Model model){
        //封装参数
        User user=(User)session.getAttribute(Contants.SSESSION_USER_KEY);
        ActivityRemark remark=new ActivityRemark();
        remark.setId(UUIDUtils.getUUID());
        remark.setNoteContent(content);
        remark.setCreateTime(DataUtils.formateDataTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Contants.REMARK_NOEDIT_FLAG);
        remark.setActivityId(id);
        //调用service方法
        int res=activityRemarkService.saveActivityRemark(remark);

        //调用service
        Activity activity=activityService.selectForDetailById(id);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkForDetailById(activity.getId());
        //把数据保存到作用域中
        model.addAttribute("activity",activity);
        model.addAttribute("remarkList",activityRemarkList);

        return "workbench/activity/detail";
    }

    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do/{Aid}/{id}")
    public String deleteActivityRemarkById(@PathVariable("Aid") String Aid,@PathVariable("id")String id,Model model){
        int res=activityRemarkService.deleteActivityRemarkById(id);
        //调用service
        Activity activity=activityService.selectForDetailById(Aid);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkForDetailById(activity.getId());
        //把数据保存到作用域中
        model.addAttribute("activity",activity);
        model.addAttribute("remarkList",activityRemarkList);

        return "workbench/activity/detail";
    }
    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public String saveEditActivityRemark(String id,String content,String aid,HttpSession session,Model model){
        //封装参数
        User user=(User)session.getAttribute(Contants.SSESSION_USER_KEY);
        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(content);
        activityRemark.setEditTime(DataUtils.formateDataTime(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG);
        activityRemarkService.saveEditActivityRemark(activityRemark);
        //调用service
        Activity activity=activityService.selectForDetailById(aid);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkForDetailById(activity.getId());
        //把数据保存到作用域中
        model.addAttribute("activity",activity);
        model.addAttribute("remarkList",activityRemarkList);

        return "workbench/activity/detail";
    }
}
