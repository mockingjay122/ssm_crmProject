package com.gj.crm.settings.service.impl;

import com.gj.crm.settings.entity.DicValue;
import com.gj.crm.settings.mapper.DicValueMapper;
import com.gj.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/18 - 17:04
 */
@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }
}
