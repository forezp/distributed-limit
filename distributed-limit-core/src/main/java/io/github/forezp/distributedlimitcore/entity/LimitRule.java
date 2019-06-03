package io.github.forezp.distributedlimitcore.entity;

import io.github.forezp.distributedlimitcore.constant.LimitType;

/**
 * Created by forezp on 2019/5/1.
 */
public class LimitRule {
    private int limitNum;
    private int seconds;
    private String identifierAddress;
    private String identifierKey;
    LimitType limitType;

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getIdentifierAddress() {
        return identifierAddress;
    }

    public void setIdentifierAddress(String identifierAddress) {
        this.identifierAddress = identifierAddress;
    }

    public String getIdentifierKey() {
        return identifierKey;
    }

    public void setIdentifierKey(String identifierKey) {
        this.identifierKey = identifierKey;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public void setLimitType(LimitType limitType) {
        this.limitType = limitType;
    }
}
