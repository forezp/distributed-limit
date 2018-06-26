package io.github.forezp.distributedlimitcore.aop;

import io.github.forezp.distributedlimitcore.annotation.Limit;
import io.github.forezp.distributedlimitcore.config.condition.AopLimitCondition;
import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.exception.LimitException;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.util.IdentifierThreadLocal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
                LimitEntity entity = new LimitEntity();
                entity.setIdentifier( limit.identifier() );
                if (StringUtils.isEmpty( IdentifierThreadLocal.get() )) {
                    entity.setIdentifier( IdentifierThreadLocal.get() );
                }
                entity.setLimtNum( limit.limtNum() );
                entity.setKey( limit.key() );
                entity.setSeconds( limit.seconds() );
                if (!limitExcutor.tryAccess( entity )) {
                    throw new LimitException( "you are not access!" );
                }
            }
        }
    }
}
