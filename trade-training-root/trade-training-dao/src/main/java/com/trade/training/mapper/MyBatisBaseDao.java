package com.trade.training.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * DAO公共基类，由MybatisGenerator自动生成请勿修改
 *
 * @author herry-zhang
 * @param <Model> The Model Class 这里是泛型不是Model类
 * @param <PK>    The Primary Key Class 如果是无主键，则可以用Model来跳过，如果是多主键则是Key类
 * @param <E>     The Example Class
 */
public interface MyBatisBaseDao<Model, PK extends Serializable, E> {
    /**
     * 获取数量
     * @param example
     * @return
     */
    long countByExample(E example);

    /**
     * 批量删除
     * @param example
     * @return
     */
    int deleteByExample(E example);

    /**
     * 按主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Model record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(Model record);

    /**
     * 选择
     * @param example
     * @return
     */
    List<Model> selectByExample(E example);

    /**
     * 选择
     * @param example
     * @return
     */
    List<Model> selectByExampleWithBLOBs(E example);

    /**
     * 选择
     * @param id
     * @return
     */
    Model selectByPrimaryKey(PK id);

    /**
     * 更新
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Model record, @Param("example") E example);

    /**
     * 更新
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Model record, @Param("example") E example);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Model record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Model record);
}