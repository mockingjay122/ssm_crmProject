package com.gj.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gj.crm.workbench.entity.Activity;
import com.gj.crm.workbench.mapper.ActivityMapper;
import com.gj.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/10/1 - 17:54
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }


    @Override
    public PageInfo<Activity> queryActivityByConditionForPage(Map<String, Object> map,Integer pageNo,Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Activity> list=activityMapper.selectActivityByConditionForPage(map);
        PageInfo<Activity> pageInfo=new PageInfo<>(list,5);
        return pageInfo;
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivity(ids);
    }

    @Override
    public Activity selectActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int updateActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public List<Activity> selectAllActivity() {
        return activityMapper.selectAllActivity();
    }

    @Override
    public List<Activity> selectAllActivityById(String[] ids) {
        return activityMapper.selectAllActivityById(ids);
    }

    @Override
    public int saveCreateActivity(List<Activity> activityList) {
        return activityMapper.insertActivityByList(activityList);
    }

    @Override
    public Activity selectForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    @Override
    public List<Activity> queryActivityByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map);
    }
}
