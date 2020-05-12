/*
 *  Copyright (c) 2020. 衷于栖 All rights reserved
 *  版权所有 衷于栖 并保留所有权利 2020.
 *  ============================================================================
 *  这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和
 *  使用。不允许对程序代码以任何形式任何目的的再发布。如果项目发布携带作者
 *  认可的特殊 LICENSE 则按照 LICENSE 执行，废除上面内容。请保留原作者信息。
 *  ============================================================================
 *  衷于栖（feedback@zhoyq.com）于 2020. 创建
 *  https://www.zhoyq.com
 */

package com.zhoyq.server.jt808.helper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
public interface BaseMapper<T> {
    @SelectProvider(type = BaseMapperSqlProvider.class, method = "selectById")
    T selectById(Class<T> clazz, Serializable id);

    @SelectProvider(type = BaseMapperSqlProvider.class, method = "selectByCondition")
    List<T> selectByCondition(Class<T> clazz, String condition, Map<String, Object> map);

    @SelectProvider(type = BaseMapperSqlProvider.class, method = "selectByConditionWithOrder")
    List<T> selectByConditionWithOrder(Class<T> clazz, String condition, String orderBy, Map<String, Object> map);

    @SelectProvider(type = BaseMapperSqlProvider.class, method = "selectPage")
    List<T> selectPage(Class<T> clazz, Page<T> page, String condition, Map<String, Object> map);

    @SelectProvider(type = BaseMapperSqlProvider.class, method = "selectCount")
    Integer selectCount(Class<T> clazz, String condition, Map<String, Object> map);

    @SelectProvider(type = BaseMapperSqlProvider.class, method = "selectAll")
    List<T> selectAll(Class<T> clazz);

    @InsertProvider(type = BaseMapperSqlProvider.class, method = "insert")
    Integer insert(T bean);

    @InsertProvider(type = BaseMapperSqlProvider.class, method = "update")
    Integer update(T bean);

    @InsertProvider(type = BaseMapperSqlProvider.class, method = "updateWithoutNull")
    Integer updateWithoutNull(T bean);

    @DeleteProvider(type = BaseMapperSqlProvider.class, method = "delete")
    Integer delete(Class<T> clazz, Serializable id);

    @DeleteProvider(type = BaseMapperSqlProvider.class, method = "deleteAll")
    Integer deleteAll(Class<T> clazz);
}
