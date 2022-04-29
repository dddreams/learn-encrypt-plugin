package com.shure.encrypt.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";

    private static final String KEY = "ec19479f79b2a225";

    private static final String IV = "ec19479f79b2a225";
    // 加密模式
    public static final String ALGORITHM = "AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encrypt(@NotNull String content) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            result = cipher.doFinal(content.getBytes(CHARSET_NAME));
        } catch (Exception e) {
            logger.error("加密异常！", e);
        }
        return Base64.encodeBase64String(result);
    }

    /**
     * 解密
     *
     * @param content
     * @return
     */
    public static String decrypt(@NotNull String content) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return new String(cipher.doFinal(Base64.decodeBase64(content)), CHARSET_NAME);
        } catch (Exception e) {
            logger.error("解密异常！", e);
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args) {
        AESUtil aes = new AESUtil();
        String contents = "{\"name\":\"shure\"}";
        String encrypt = aes.encrypt(contents);
        System.out.println("加密后:" + encrypt);
        String decrypt = aes.decrypt(encrypt);
        System.out.println("解密后:" + decrypt);
    }

}
