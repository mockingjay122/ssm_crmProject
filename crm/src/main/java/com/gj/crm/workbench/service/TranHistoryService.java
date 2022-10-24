package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.TranHistory;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 19:18
 */
public interface TranHistoryService {
    //查询交易历史信息
    List<TranHistory> queryTranHistoryForDetailById(String tranId);
}
