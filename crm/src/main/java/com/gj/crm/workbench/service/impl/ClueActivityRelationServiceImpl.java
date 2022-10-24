package com.gj.crm.workbench.service.impl;

import com.gj.crm.workbench.entity.ClueActivityRelation;
import com.gj.crm.workbench.mapper.ClueActivityRelationMapper;
import com.gj.crm.workbench.mapper.ClueRemarkMapper;
import com.gj.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/20 - 16:03
 */
@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;


    @Override
    public int saveClueActivityRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(relation);
    }
}
