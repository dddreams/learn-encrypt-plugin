package com.shure.encrypt.plugin;

import com.shure.encrypt.utils.EncryptDecryptUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args={Statement.class})
})
@Component
public class ResultInterceptor implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (Objects.isNull(result)){
            return null;
        }
        if (result instanceof List) {
            EncryptDecryptUtils.fieldListDecrypt(result);
        }else if(EncryptDecryptUtils.notToDecrypt(result)) {
            EncryptDecryptUtils.decrypt(EncryptDecryptUtils.getFields(result.getClass()), result);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
