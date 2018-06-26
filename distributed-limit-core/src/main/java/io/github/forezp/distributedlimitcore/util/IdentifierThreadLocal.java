package io.github.forezp.distributedlimitcore.util;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
public class IdentifierThreadLocal {

    private static ThreadLocal<String> identifierThreadLocal = new ThreadLocal<>();
    //key为该对象identifierThreadLocal,存储在Thread的变量ThreadLocal.Map中，
    // 所以一个线程可以还有很多个ThreadLocal，都存储在map中


    public static void set(String identifier) {
        identifierThreadLocal.set( identifier );
    }

    public static String get() {
        return identifierThreadLocal.get();
    }
}
