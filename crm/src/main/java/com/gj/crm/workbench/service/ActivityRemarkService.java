package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.Activity;
import com.gj.crm.workbench.entity.ActivityRemark;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/16 - 19:33
 */
public interface ActivityRemarkService {
    //*查询市场备注
    List<ActivityRemark> queryActivityRemarkForDetailById(String activityId);
    //保存市场活动备注
    int saveActivityRemark(ActivityRemark remark);
    //根据id删除市场活动备注
    int deleteActivityRemarkById(String id);
    //修改市场活动备注
    int saveEditActivityRemark(ActivityRemark remark);
}
