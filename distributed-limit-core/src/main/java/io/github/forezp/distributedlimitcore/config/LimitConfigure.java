package io.github.forezp.distributedlimitcore.config;


import io.github.forezp.distributedlimitcore.config.condition.GuavaLimitCondition;
import io.github.forezp.distributedlimitcore.config.condition.RedisLimitCondition;
import io.github.forezp.distributedlimitcore.limit.GuavaLimitExcutor;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.limit.RedisLimitExcutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
@Configuration
@ComponentScan("io.github.forezp")
public class LimitConfigure {

    @Bean
    @Conditional(GuavaLimitCondition.class)
    public LimitExcutor guavaLimitExcutor() {
        return new GuavaLimitExcutor();
    }

    @Bean
    @Conditional(RedisLimitCondition.class)
    public LimitExcutor redisLimitExcutor(StringRedisTemplate stringRedisTemplate) {
        return new RedisLimitExcutor( stringRedisTemplate );
    }


}
