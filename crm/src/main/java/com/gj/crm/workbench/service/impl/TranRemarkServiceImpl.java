package com.gj.crm.workbench.service.impl;

import com.gj.crm.workbench.entity.TranRemark;
import com.gj.crm.workbench.mapper.TranRemarkMapper;
import com.gj.crm.workbench.service.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 19:12
 */
@Service("tranRemarkService")
public class TranRemarkServiceImpl implements TranRemarkService {

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public List<TranRemark> queryTranRrmarkForDetailById(String id) {
        return tranRemarkMapper.selectTranRrmarkForDetailById(id);
    }
}
