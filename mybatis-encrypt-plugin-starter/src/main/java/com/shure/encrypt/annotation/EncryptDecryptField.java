package com.shure.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 加密字段注解
 */
@Documented
@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptDecryptField {

    /**
     * 字段是否使用, 分割符号拼接而成(SQL 查询使用 GROUP_CONCAT)
     * @return boolean
     */
    boolean split() default false;
}
