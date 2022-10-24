package com.gj.crm.workbench.service;

import com.gj.crm.workbench.entity.Clue;

import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/10/18 - 17:55
 */
public interface ClueService {
    //保存线索
    public int saveCreateClue(Clue clue);
    //根据条件查询
    public List<Clue> queryClueByCondition(Map<String,Object>map);
    //查询线索明细
    public Clue queryClueDetailById(String id);
    //保存创建线索转换
    void saveConvert(Map<String,Object> map);

}
