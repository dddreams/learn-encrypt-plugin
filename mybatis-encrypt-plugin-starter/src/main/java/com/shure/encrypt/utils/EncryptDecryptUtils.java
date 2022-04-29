package com.shure.encrypt.utils;


import com.shure.encrypt.annotation.EncryptDecryptClass;
import com.shure.encrypt.annotation.EncryptDecryptField;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EncryptDecryptUtils {

    /**
     * 多 field 加密方法
     *
     * @param declaredFields 字段
     * @param parameterObject 类
     * @return T
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> void encrypt(Field[] declaredFields, T parameterObject) throws IllegalAccessException {
        for (Field field : declaredFields) {
            encrypt(field, parameterObject);
        }
    }

    /**
     * 多个 field 解密方法
     *
     *
     * @param declaredFields 解密字段
     * @param result 结果
     * @throws IllegalAccessException IllegalAccessException
     */
    public static void decrypt(Field[] declaredFields, Object result) throws IllegalAccessException {
        for (Field field : declaredFields) {
            decrypt(field, result);
        }
    }

    /**
     * 单个 field 加密方法
     *
     * @param field 字段
     * @param parameterObject 类
     * @return T
     * @throws IllegalAccessException IllegalAccessException
     */
    private static <T> void encrypt(Field field, T parameterObject) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(parameterObject);
        if (object instanceof String) {
            field.set(parameterObject, AESUtil.encrypt((String)object));
        }
    }

    /**
     * 单个 field 解密方法
     *
     * @param field 字段
     * @param result 结果
     * @throws IllegalAccessException IllegalAccessException
     */
    private static void decrypt(Field field, Object result) throws IllegalAccessException {
        fieldObjectDecrypt(field, result);
        field.setAccessible(true);
        Object object = field.get(result);
        if (Objects.isNull(object)) {
            return;
        }
        if (object instanceof String) {
            EncryptDecryptField decryptField = field.getAnnotation(EncryptDecryptField.class);
            if (!decryptField.split()) {
                field.set(result, AESUtil.decrypt((String)object));
                return;
            }
            String[] strings = ((String) object).split(",");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0, length = strings.length - 1; i < length; i++) {
                stringBuilder.append(AESUtil.decrypt(strings[i])).append(",");
            }
            stringBuilder.append(AESUtil.decrypt(strings[strings.length - 1]));
            field.set(result, stringBuilder.toString());
        }
    }

    /**
     * 是否解密
     * @param object 对象
     * @return 是否解密
     */
    public static boolean notToDecrypt(Object object){
        Class<?> objectClass = object.getClass();
        EncryptDecryptClass encryptDecryptClass = objectClass.getAnnotation(EncryptDecryptClass.class);
        return Objects.isNull(encryptDecryptClass);
    }

    /**
     * 获取有@EncryptDecryptField 注解的字段
     * @param objectClass 类
     * @return 字段
     */
    public static Field[] getFields(Class<?> objectClass) {
        Field[] fields = Arrays.stream(objectClass.getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getAnnotation(EncryptDecryptField.class)))
                .toArray(Field[]::new);
        Class<?> superClass = objectClass.getSuperclass();
        if (Object.class == superClass) {
            return fields;
        }

        return ArrayUtils.addAll(fields, Arrays.stream(superClass.getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getAnnotation(EncryptDecryptField.class)))
                .toArray(Field[]::new));
    }

    /**
     * 字段对象的解密
     * @param field 字段
     * @param result 数据
     * @throws IllegalAccessException IllegalAccessException
     */
    private static void fieldObjectDecrypt(Field field, Object result) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(result);
        if (object instanceof List) {
            fieldListDecrypt(object);
            return;
        }
        Class<?> objectClass = field.getType();
        EncryptDecryptClass encryptDecryptClass = objectClass.getAnnotation(EncryptDecryptClass.class);
        if (Objects.nonNull(encryptDecryptClass)) {
            decrypt(getFields(objectClass), field.get(result));
        }
    }

    /**
     * 字段集合的解密
     * @param object 集合数据
     * @throws IllegalAccessException IllegalAccessException
     */
    public static void fieldListDecrypt(Object object) throws IllegalAccessException {
        ArrayList resultList = (ArrayList) object;
        if (resultList.isEmpty() || EncryptDecryptUtils.notToDecrypt(resultList.get(0))){
            return;
        }
        Field[] declaredFields = EncryptDecryptUtils.getFields(resultList.get(0).getClass());
        if (ArrayUtils.isEmpty(declaredFields)) {
            return;
        }
        for (Object o : resultList) {
            decrypt(declaredFields, o);
        }
    }

    private EncryptDecryptUtils() {}
}
