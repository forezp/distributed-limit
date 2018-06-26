package io.github.forezp.distributedlimitcore.config.condition;

import io.github.forezp.distributedlimitcore.constant.ConfigConstant;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
public class AopLimitCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String limitType = conditionContext.getEnvironment().getProperty( ConfigConstant.LIMIT_TYPE );
        return !StringUtils.isEmpty( limitType );
    }
}
