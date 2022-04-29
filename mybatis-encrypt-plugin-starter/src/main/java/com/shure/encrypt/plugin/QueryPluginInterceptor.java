package com.shure.encrypt.plugin;

import com.shure.encrypt.utils.MyBatisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Statement;

@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {Statement.class, ResultHandler.class}
        ),
        @Signature(
                type = StatementHandler.class,
                method = "update",
                args = {Statement.class}
        )}
)
@Slf4j
public class QueryPluginInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1、获得当前执行的 sql 语句
        StatementHandler statementHandler = MyBatisUtils.getNoPorxyTarget(invocation.getTarget());
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql().replace("\n", "");
        log.info("[SQL] - exec sql - [{}]", sql);

        // 2、记录当前sql 的执行时间，其实就是 query 方法执行的时间
        long begin = System.currentTimeMillis();
        Object proceed = invocation.proceed();// 放行，执行实际的方法
        long end = System.currentTimeMillis();
        log.info("[SQL] - sql exec time - [{}s]", BigDecimal.valueOf(end - begin).divide(BigDecimal.valueOf(1000)).setScale(6, RoundingMode.DOWN).doubleValue());
        return proceed;
    }

}
