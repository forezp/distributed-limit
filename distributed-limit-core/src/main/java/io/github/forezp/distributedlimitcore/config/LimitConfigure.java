package io.github.forezp.distributedlimitcore.config;


import io.github.forezp.distributedlimitcore.collector.ConsolLimitDataCollector;
import io.github.forezp.distributedlimitcore.collector.LimitDataCollector;
import io.github.forezp.distributedlimitcore.config.condition.FilterLImitConditon;
import io.github.forezp.distributedlimitcore.config.condition.GuavaLimitCondition;
import io.github.forezp.distributedlimitcore.config.condition.RedisLimitCondition;
import io.github.forezp.distributedlimitcore.filter.RateLimitFilter;
import io.github.forezp.distributedlimitcore.limit.GuavaLimitExcutor;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.limit.RedisLimitExcutor;
import io.github.forezp.distributedlimitcore.rule.LimitEbtityBuilderImpl;
import io.github.forezp.distributedlimitcore.rule.LimitEntityBuilder;
import io.github.forezp.distributedlimitcore.rule.LimitRuleLoader;
import io.github.forezp.distributedlimitcore.rule.LocalLimitRuleLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
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

    @Conditional(FilterLImitConditon.class)
    @Configuration
    static class FilterLimitConfig{

        @Bean
        @Conditional(FilterLImitConditon.class)
        @ConditionalOnMissingBean
        public LimitRuleLoader limitRuleLoader(){
            return new LocalLimitRuleLoader();
        }
        @Bean
        @Conditional(FilterLImitConditon.class)
        @ConditionalOnMissingBean
       // @ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = true)
        public LimitEntityBuilder limitEntityBuilder(LimitRuleLoader limitRuleLoader){
            return new LimitEbtityBuilderImpl(limitRuleLoader);
        }

        @Bean
        @Conditional(FilterLImitConditon.class)
        @ConditionalOnMissingBean
        public LimitDataCollector limitDataCollector(){
            return  new ConsolLimitDataCollector();
        }



        @Bean
        @Conditional(FilterLImitConditon.class)
        public RateLimitFilter rateLimitFilter(LimitExcutor limitExcutor,LimitEntityBuilder limitEntityBuilder,LimitDataCollector limitDataCollector){
            RateLimitFilter rateLimitFilter=new RateLimitFilter(limitExcutor,limitEntityBuilder,limitDataCollector);

            return rateLimitFilter;
        }

        @Bean
        @Conditional(FilterLImitConditon.class)
        public FilterRegistrationBean filterRegistrationBean(RateLimitFilter rateLimitFilter){
            FilterRegistrationBean filterRegistrationBean=new  FilterRegistrationBean(rateLimitFilter);
            filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return filterRegistrationBean;
        }
    }


}
