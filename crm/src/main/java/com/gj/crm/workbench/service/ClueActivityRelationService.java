package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.ClueActivityRelation;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/20 - 16:03
 */
public interface ClueActivityRelationService {

    //批量保存市场活动，线索关系
    int saveClueActivityRelationByList(List<ClueActivityRelation> list);
    //根据id删除
    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);
}
