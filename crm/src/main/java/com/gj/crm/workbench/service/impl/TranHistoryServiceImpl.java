package com.gj.crm.workbench.service.impl;

import com.gj.crm.workbench.entity.TranHistory;
import com.gj.crm.workbench.mapper.TranHistoryMapper;
import com.gj.crm.workbench.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 19:19
 */
@Service("tranHistoryService")
public class TranHistoryServiceImpl implements TranHistoryService {

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public List<TranHistory> queryTranHistoryForDetailById(String tranId) {
        return tranHistoryMapper.selectTranHistoryForDetailById(tranId);
    }
}
