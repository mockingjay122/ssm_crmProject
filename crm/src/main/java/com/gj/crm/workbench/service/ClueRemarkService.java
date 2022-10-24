package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.ClueRemark;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/19 - 17:20
 */
public interface ClueRemarkService {
    //查询线索明细
    List<ClueRemark> queryClueRemarkForDetail(String clueId);

}
