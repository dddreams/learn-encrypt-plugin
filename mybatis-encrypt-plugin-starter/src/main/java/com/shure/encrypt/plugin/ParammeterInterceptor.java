package com.shure.encrypt.plugin;

import com.shure.encrypt.annotation.EncryptDecryptClass;
import com.shure.encrypt.utils.EncryptDecryptUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Properties;

@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class)
})
@Component
public class ParammeterInterceptor  implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!(invocation.getTarget() instanceof ParameterHandler)) {
            return invocation.proceed();
        }
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Object parameterObject = parameterHandler.getParameterObject();
        if (Objects.isNull(parameterObject)) {
            return invocation.proceed();
        }
        Class<?> parameterObjectClass = parameterObject.getClass();
        EncryptDecryptClass encryptDecryptClass = parameterObjectClass.getAnnotation(EncryptDecryptClass.class);
        if (Objects.nonNull(encryptDecryptClass)){
            EncryptDecryptUtils.encrypt(EncryptDecryptUtils.getFields(parameterObjectClass), parameterObject);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
