package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.TranRemark;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 19:11
 */
public interface TranRemarkService {

    //查询交易备注明细
    List<TranRemark> queryTranRrmarkForDetailById(String id);
}
