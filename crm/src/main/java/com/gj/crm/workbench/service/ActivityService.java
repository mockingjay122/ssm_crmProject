package com.gj.crm.workbench.service;

import com.github.pagehelper.PageInfo;
import com.gj.crm.workbench.entity.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/10/1 - 17:50
 */
public interface ActivityService  {



        //添加市场活动
        int saveActivity(Activity activity);
        //根据条件分页查询市场活动
        //List<Activity> queryActivityByConditionForPage(Map<String, Object> map);
        //根据条件查询市场活动的总条数
        //int selectCountActivityByCondition(Map<String, Object> map);
        //分页插件使用查询
        PageInfo<Activity> queryActivityByConditionForPage(Map<String, Object> map,Integer pageNo,Integer pageSize);
        //根据id删除
        int deleteActivityByIds(String [] ids);
        //跟据id查询
        Activity selectActivityById(String id);
        //修改市场活动
        int updateActivity(Activity activity);
        //查询所有市场活动
        List<Activity> selectAllActivity();
        //id查询所有市场活动
        List<Activity> selectAllActivityById(String []ids);
        //导入市场活动
        int saveCreateActivity(List<Activity> activityList);
        //查询市场明细根据id
        Activity selectForDetailById(String id);
        //查询市场活动明细根据线索id
        List<Activity> queryActivityForDetailByClueId(String clueId);
        //根据名称模糊查询且吧clueId有关的市场活动排除
        List<Activity> queryActivityByNameClueId(Map<String,Object> map);
        //根据ids查询市场明细
        List<Activity> queryActivityForDetailByIds(String [] ids);
        //转换查询线索 为了市场活动
        List<Activity> queryActivityForConvertByNameClueId(Map<String,Object> map);
}
