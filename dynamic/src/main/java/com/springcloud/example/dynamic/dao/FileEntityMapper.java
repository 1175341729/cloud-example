package com.springcloud.example.dynamic.dao;

import com.springcloud.example.dynamic.model.FileEntity;
import com.springcloud.example.dynamic.model.FileEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int countByExample(FileEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int deleteByExample(FileEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int insert(FileEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int insertSelective(FileEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    List<FileEntity> selectByExample(FileEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    FileEntity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int updateByExampleSelective(@Param("record") FileEntity record, @Param("example") FileEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int updateByExample(@Param("record") FileEntity record, @Param("example") FileEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int updateByPrimaryKeySelective(FileEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file
     *
     * @mbggenerated Tue Sep 25 13:58:10 CST 2018
     */
    int updateByPrimaryKey(FileEntity record);
}