package io.github.forezp.distributedlimitcore.aop;

import io.github.forezp.distributedlimitcore.annotation.Limit;
import io.github.forezp.distributedlimitcore.config.condition.AopLimitCondition;
import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.entity.LimitResult;
import io.github.forezp.distributedlimitcore.exception.LimitException;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.util.IdentifierThreadLocal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-25
 **/
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Conditional(AopLimitCondition.class)
public class LimitAop {

    private static Logger log = LoggerFactory.getLogger( LimitAop.class );

    @Autowired
    LimitExcutor limitExcutor;

    @Pointcut("@annotation(io.github.forezp.distributedlimitcore.annotation.Limit)")
    private void check() {

    }

    @Before("check()")
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();


        Annotation[] methodAnnotations = method.getDeclaredAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof Limit) {
                Limit limit = (Limit) annotation;

                String identifier = parseKey( limit.identifier(), method, joinPoint.getArgs() );
                LimitEntity entity = new LimitEntity();
                entity.setIdentifier( identifier );
                if (!StringUtils.isEmpty( IdentifierThreadLocal.get() )) {
                    entity.setIdentifier( IdentifierThreadLocal.get() );
                }
                entity.setLimtNum( limit.limtNum() );
                String key = parseKey( limit.key(), method, joinPoint.getArgs() );
                entity.setKey( key );
                entity.setSeconds( limit.seconds() );
                if (limitExcutor.tryAccess( entity ).getResultType()!= LimitResult.ResultType.SUCCESS) {
                    throw new LimitException( "you are not access!" );
                }
                if (!StringUtils.isEmpty( IdentifierThreadLocal.get() )) {
                    entity.setIdentifier( IdentifierThreadLocal.get() );
                }
                if (!StringUtils.isEmpty( IdentifierThreadLocal.get() )) {
                    IdentifierThreadLocal.remove();
                }
            }
        }
    }


    /**
     * 该方法只适用cglib
     *
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {

        //如果不含# 则判断不为spel表达式，这有一定的误差，但是够用
        // fixme
        //TODO
        if (!key.contains( "#" ) || StringUtils.isEmpty( key )) {
            return key;
        }
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames( method );

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable( paraNameArr[i], args[i] );
        }
        return parser.parseExpression( key ).getValue( context, String.class );
    }

}