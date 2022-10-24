package com.gj.crm.settings.service;

import com.gj.crm.settings.entity.DicValue;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/18 - 17:04
 */
public interface DicValueService {
    //查询数据字典
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
