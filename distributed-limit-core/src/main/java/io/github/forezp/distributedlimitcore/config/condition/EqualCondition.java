package io.github.forezp.distributedlimitcore.config.condition;


import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
public class EqualCondition implements org.springframework.context.annotation.Condition {

    private String key;
    private String value;

    public EqualCondition(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String value = context.getEnvironment().getProperty( key );
        if (value == null) {
            return false;
        }
        return value.equals( this.value );
    }
}
