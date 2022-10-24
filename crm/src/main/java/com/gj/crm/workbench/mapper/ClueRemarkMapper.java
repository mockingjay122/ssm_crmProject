package com.gj.crm.workbench.mapper;

import com.gj.crm.workbench.entity.ClueRemark;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClueRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Wed Oct 19 17:13:08 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Wed Oct 19 17:13:08 CST 2022
     */
    int insert(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Wed Oct 19 17:13:08 CST 2022
     */
    int insertSelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Wed Oct 19 17:13:08 CST 2022
     */
    ClueRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Wed Oct 19 17:13:08 CST 2022
     */
    int updateByPrimaryKeySelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Wed Oct 19 17:13:08 CST 2022
     */
    int updateByPrimaryKey(ClueRemark record);
    /**
     *
     */
    List<ClueRemark> selectClueRemarkForDetail(String clueId);
    /**
     * 根据线索id查询线索备注
     */
    List<ClueRemark> selectClueRemarkByClueId(String clueId);
    /**
     * 根据线索id删除线索备注
     */
    int deleteClueRemarkByClueId(String clueId);
}