package io.github.forezp.distributedlimitcore.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Limit {

    String identifier() default "";

    String key() default "";

    int limtNum() default 100;

    int seconds() default 60;



}
