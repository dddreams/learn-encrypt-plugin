package com.shure.encrypt.base;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类的统一父类
 *
 * @author Shure
 * @date 2022/1/24 10:22
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private Date createTime = new Date();
    private Date updateTime = new Date();
    private Integer status = 0;
    private Integer delFlag = 0;
}
