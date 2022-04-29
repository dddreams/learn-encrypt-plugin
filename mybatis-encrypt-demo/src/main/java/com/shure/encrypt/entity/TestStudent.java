package com.shure.encrypt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shure.encrypt.annotation.EncryptDecryptClass;
import com.shure.encrypt.annotation.EncryptDecryptField;
import com.shure.encrypt.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * (TestStudent)表实体类
 *
 * @author makejava
 * @since 2022-04-27 09:38:13
 */
@Data
@Accessors(chain = true)
@TableName("test_student")
@EncryptDecryptClass
public class TestStudent extends BaseEntity {
    //主键
    @TableId(type = IdType.AUTO)
    private Long sid;
    //学生姓名
    @EncryptDecryptField
    private String name;
    //性别
    private Integer sex;
    //邮箱
    @EncryptDecryptField
    private String email;
}

