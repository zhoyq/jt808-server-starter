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

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Slf4j
public class BaseMapperSqlProvider {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private boolean checkId(Field field) {
        Id idInfo = field.getAnnotation(Id.class);
        return idInfo!=null;
    }

    private boolean checkIdAndGen(Field field) {
        Id idInfo = field.getAnnotation(Id.class);
        if(idInfo != null){
            GeneratedValue gv = field.getAnnotation(GeneratedValue.class);
            return gv != null;
        }else{
            return false;
        }
    }

    private <T> void fillTableName(SQL sql, Class<T> clazz){
        sql.FROM(tableName(clazz));
    }

    private <T> String tableName(Class<T> clazz){
        Table tableInfo = clazz.getAnnotation(Table.class);
        return tableInfo.schema() + "." + tableInfo.name();
    }

    private <T> void fillSelectColumn(SQL sql, Class<T> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        for(int i=0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            Column columnInfo = field.getAnnotation(Column.class);
            if(columnInfo != null){
                String columnName;
                if("".equals(columnInfo.name())){
                    columnName = humpToLine(field.getName());
                }else{
                    columnName = columnInfo.name();
                }
                if(columnName.equals(field.getName())){
                    sql.SELECT(columnName);
                } else {
                    sql.SELECT(columnName + " as " + field.getName());
                }
            }
        }
    }

    private <T> void fillWhereId(SQL sql, Class<T> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        for(int i=0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            Id idInfo = field.getAnnotation(Id.class);
            if(idInfo != null){
                Column columnInfo = field.getAnnotation(Column.class);
                String columnName;
                if("".equals(columnInfo.name())){
                    columnName = humpToLine(field.getName());
                }else{
                    columnName = columnInfo.name();
                }
                sql.WHERE(columnName + "=#{id}");
                break;
            }
        }
    }

    private <T> void fillWhereIdName(SQL sql, Class<T> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        for(int i=0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            Id idInfo = field.getAnnotation(Id.class);
            if(idInfo != null){
                Column columnInfo = field.getAnnotation(Column.class);
                String columnName;
                if("".equals(columnInfo.name())){
                    columnName = humpToLine(field.getName());
                }else{
                    columnName = columnInfo.name();
                }
                sql.WHERE(columnName + "=#{" + field.getName() + "}");
                break;
            }
        }
    }

    private <T> void fillValues(SQL sql, T bean) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for(int i=0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            if(checkIdAndGen(field)){
                continue;
            }
            Column columnInfo = field.getAnnotation(Column.class);
            if(columnInfo != null){
                String columnName;
                if("".equals(columnInfo.name())){
                    columnName = humpToLine(field.getName());
                }else{
                    columnName = columnInfo.name();
                }
                sql.VALUES(columnName, "#{"+field.getName()+"}");
            }
        }
    }

    private <T> void fillSets(SQL sql, T bean, boolean flag) throws IllegalAccessException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for(int i=0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            if(checkId(field)){
                continue;
            }
            Column columnInfo = field.getAnnotation(Column.class);
            if(columnInfo != null){
                String columnName;
                if("".equals(columnInfo.name())){
                    columnName = humpToLine(field.getName());
                }else{
                    columnName = columnInfo.name();
                }
                if(flag){
                    field.setAccessible(true);
                    Object val = field.get(bean);
                    if(val != null){
                        sql.SET(columnName + "=#{"+field.getName()+"}");
                    }
                }else{
                    sql.SET(columnName + "=#{"+field.getName()+"}");
                }
            }
        }
    }

    public <T> String selectById(Class<T> clazz){
        SQL sql = new SQL();
        fillTableName(sql, clazz);
        fillSelectColumn(sql, clazz);
        fillWhereId(sql, clazz);
        return sql.toString();
    }

    public <T> String selectByCondition(Class<T> clazz, String condition){
        SQL sql = new SQL();
        fillTableName(sql, clazz);
        fillSelectColumn(sql, clazz);
        sql.WHERE(condition);
        return sql.toString();
    }

    public <T> String selectByConditionWithOrder(Class<T> clazz, String condition, String orderBy){
        SQL sql = new SQL();
        fillTableName(sql, clazz);
        fillSelectColumn(sql, clazz);
        sql.WHERE(condition);
        sql.ORDER_BY(orderBy);
        return sql.toString();
    }

    public <T> String selectPage(Class<T> clazz, Page<T> page, String condition){
        SQL sql = new SQL();
        fillTableName(sql, clazz);
        fillSelectColumn(sql, clazz);
        sql.WHERE(condition);
        sql.ORDER_BY(page.getOrderBy() + " " + page.getDir());
        return sql.toString() + " limit " + page.getStart() + "," + page.getLimit();
    }

    public <T> String selectCount(Class<T> clazz, String condition){
        SQL sql = new SQL();
        fillTableName(sql, clazz);
        sql.SELECT("count(*)");
        sql.WHERE(condition);
        return sql.toString();
    }

    public <T> String selectAll(Class<T> clazz){
        SQL sql = new SQL();
        fillTableName(sql, clazz);
        fillSelectColumn(sql, clazz);
        return sql.toString();
    }

    public <T> String insert(T bean){
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName(bean.getClass()));
        fillValues(sql, bean);
        return sql.toString();
    }

    public <T> String update(T bean){
        SQL sql = new SQL();
        sql.UPDATE(tableName(bean.getClass()));
        try {
            fillSets(sql, bean, false);
        } catch (IllegalAccessException e) {
            log.warn(e.getMessage());
        }
        fillWhereIdName(sql, bean.getClass());
        return sql.toString();
    }

    public <T> String updateWithoutNull(T bean){
        SQL sql = new SQL();
        sql.UPDATE(tableName(bean.getClass()));
        try {
            fillSets(sql, bean, true);
        } catch (IllegalAccessException e) {
            log.warn(e.getMessage());
        }
        fillWhereIdName(sql, bean.getClass());
        return sql.toString();
    }

    public <T> String delete(Class<T> clazz){
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName(clazz));
        fillWhereId(sql, clazz);
        return sql.toString();
    }

    public <T> String deleteAll(Class<T> clazz){
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName(clazz));
        return sql.toString();
    }
}
