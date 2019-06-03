package io.github.forezp.distributedlimitcore.entity;

import io.github.forezp.distributedlimitcore.constant.LimitType;

import java.util.concurrent.TimeUnit;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-25
 **/
public class LimitEntity {


    private LimitType limitType;
    private String identifier;

    private String key;

    private int limtNum;

    private int seconds;//多少秒

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    public int getLimtNum() {
        return limtNum;
    }

    public void setLimtNum(int limtNum) {
        this.limtNum = limtNum;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public void setLimitType(LimitType limitType) {
        this.limitType = limitType;
    }
}
