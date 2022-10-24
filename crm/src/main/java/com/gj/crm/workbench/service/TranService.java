package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.FinalAo;
import com.gj.crm.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 18:03
 */
public interface TranService {

    //保存交易
    void saveCreateTran(Map<String ,Object> map);
    //查询交易明细
    Tran queryTranForDetailById(String id);
    //交易图标信息
    List<FinalAo> queryCountOfTranByGroup();
}
