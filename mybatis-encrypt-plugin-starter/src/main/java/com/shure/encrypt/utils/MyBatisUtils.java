package com.shure.encrypt.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * @author Shure
 * @date 2022/1/26 16:58
 */
public class MyBatisUtils {

    public static <T> T getNoPorxyTarget(Object target) {
        MetaObject invoMeteObject = SystemMetaObject.forObject(target);
        while (invoMeteObject.hasGetter("h")) {
            target = invoMeteObject.getValue("h.target");
            invoMeteObject = SystemMetaObject.forObject(target);
        }
        return (T) target;
    }

    /**
     * 获取sql 语句中主表的 from 关键字的下标
     *
     * @param sql
     * @return
     */
    public static int getFromIndex(int beginIndex, String sql) {
        int fromIndex = sql.indexOf("from", beginIndex);
        if (fromIndex == -1) {
            return -1;
        }

        int count = 0; // 括号的计数器
        int selectIndex = fromIndex;
        int bIndex = -1; // 正括号的下标
        int eIndex = -1; // 反括号的下标
        while ((bIndex = sql.lastIndexOf("(", selectIndex)) != -1) {
            count++;
            selectIndex = bIndex - 1;
        }

        selectIndex = fromIndex;
        while ((eIndex = sql.lastIndexOf(")", selectIndex)) != -1) {
            count--;
            selectIndex = eIndex - 1;
        }

        if (count == 0) {
            return fromIndex;
        } else {
            return getFromIndex(fromIndex + 1, sql);
        }
    }
}
