package com.gj.crm.workbench.mapper;

import com.gj.crm.workbench.entity.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Oct 23 18:52:32 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Oct 23 18:52:32 CST 2022
     */
    int insert(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Oct 23 18:52:32 CST 2022
     */
    int insertSelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Oct 23 18:52:32 CST 2022
     */
    TranHistory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Oct 23 18:52:32 CST 2022
     */
    int updateByPrimaryKeySelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Oct 23 18:52:32 CST 2022
     */
    int updateByPrimaryKey(TranHistory record);
    /**
     * 添加交易历史
     */
    int insertTranHistory(TranHistory tranHistory);
    /**
     * 根据id查询交易历史信息
     *
     */
    List<TranHistory> selectTranHistoryForDetailById(String tranId);
}