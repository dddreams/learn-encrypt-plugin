 ## 简介
 基于Mybatis的数据加解密插件，利用Mybatis的拦截器在数据入库和出库时做处理

 ## 使用

在实体类和字段上添加`@EncryptDecryptClass`和`@EncryptDecryptField`注解，保存时自动加密添加注解的字段，查询时自动解密，业务无感知。
 ```java
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
 ```