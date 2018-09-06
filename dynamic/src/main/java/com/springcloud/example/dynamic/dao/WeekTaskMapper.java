package com.springcloud.example.dynamic.dao;

import com.springcloud.example.dynamic.model.WeekTask;
import com.springcloud.example.dynamic.model.WeekTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeekTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int countByExample(WeekTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int deleteByExample(WeekTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int deleteByPrimaryKey(Integer taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int insert(WeekTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int insertSelective(WeekTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    List<WeekTask> selectByExample(WeekTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    WeekTask selectByPrimaryKey(Integer taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int updateByExampleSelective(@Param("record") WeekTask record, @Param("example") WeekTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int updateByExample(@Param("record") WeekTask record, @Param("example") WeekTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int updateByPrimaryKeySelective(WeekTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table r_week_task
     *
     * @mbggenerated Thu Sep 06 13:59:28 CST 2018
     */
    int updateByPrimaryKey(WeekTask record);
}