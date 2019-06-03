package io.github.forezp.distributedlimitcore.util;

import io.github.forezp.distributedlimitcore.entity.LimitEntity;

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


    public static String getKey(LimitEntity limitEntity){
        String key ;
        switch (limitEntity.getLimitType()) {
            case GLOBAL:
                key = "global";
                break;
            case URL:
                key = limitEntity.getKey();
                break;
            case USER:
                key = limitEntity.getIdentifier();
                break;
            case USER_URL:
                key = KeyUtil.compositeKey(limitEntity.getIdentifier() == null ? "nobody" : limitEntity.getIdentifier(), limitEntity.getKey());
                break;
            default:
                key = null;
                break;
        }
        return key;
    }



}
