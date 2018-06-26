package io.github.forezp.distributedlimitcore.util;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
public class KeyUtil {

    public static String compositeKey(String identifier, String key) {
        return identifier + ":" + key + ":";
    }
}
