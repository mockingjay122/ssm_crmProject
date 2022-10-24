package com.gj.crm.workbench.service.impl;

import com.gj.crm.workbench.entity.ClueRemark;
import com.gj.crm.workbench.mapper.ClueRemarkMapper;
import com.gj.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/19 - 17:20
 */
@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> queryClueRemarkForDetail(String clueId) {
        return clueRemarkMapper.selectClueRemarkForDetail(clueId);
    }
}
