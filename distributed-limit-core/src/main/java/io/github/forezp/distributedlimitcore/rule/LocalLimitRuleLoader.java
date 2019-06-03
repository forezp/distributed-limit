package io.github.forezp.distributedlimitcore.rule;

import io.github.forezp.distributedlimitcore.entity.LimitRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.github.forezp.distributedlimitcore.constant.LimitType.*;

/**
 * Created by forezp on 2019/5/1.
 */
public class LocalLimitRuleLoader implements LimitRuleLoader {

    @Value("${limit.rule.global.num:}")
    protected String globalNum;
    @Value("${limit.rule.global.seconds:}")
    protected String globalSeconds;

    @Value("${limit.rule.url.num:}")
    protected String urlNum;
    @Value("${limit.rule.url.seconds:}")
    protected String urlSeconds;
    @Value("${limit.rule.user.num:}")
    protected String userNum;
    @Value("${limit.rule.user.seconds:}")
    protected String userSeconds;
    @Value("${limit.rule.user_url.num:}")
    protected String userUrlNum;
    @Value("${limit.rule.user_url.seconds:}")
    protected String userUrlSeconds;
    @Value("${limit.rule.identifier.address:header}")
    protected String identifierAddress;
    @Value("${limit.rule.identifier.key:userId}")
    protected String identifierkey;

    @Override
    public List<LimitRule> load() {

        List<LimitRule> limitRules = new ArrayList<>();
        if (!StringUtils.isEmpty(globalNum)) {
            LimitRule limitRule = new LimitRule();
            limitRule.setLimitType(GLOBAL);
            limitRule.setLimitNum(Integer.parseInt(globalNum));
            limitRule.setSeconds(Integer.parseInt(globalSeconds));
            limitRules.add(limitRule);

        }
        if (!StringUtils.isEmpty(urlNum)) {
            LimitRule limitRule = new LimitRule();
            limitRule.setLimitType(URL);
            limitRule.setLimitNum(Integer.parseInt(urlNum));
            limitRule.setSeconds(Integer.parseInt(urlSeconds));
            limitRules.add(limitRule);
        }
        if (!StringUtils.isEmpty(userNum)) {
            LimitRule limitRule = new LimitRule();
            limitRule.setLimitType(USER);
            limitRule.setLimitNum(Integer.parseInt(userNum));
            limitRule.setSeconds(Integer.parseInt(userSeconds));
            limitRule.setIdentifierAddress(identifierAddress);
            limitRule.setIdentifierKey(identifierkey);
            limitRules.add(limitRule);
        }
        if (!StringUtils.isEmpty(userUrlNum)) {
            LimitRule limitRule = new LimitRule();
            limitRule.setLimitType(USER_URL);
            limitRule.setLimitNum(Integer.parseInt(userUrlNum));
            limitRule.setSeconds(Integer.parseInt(userUrlSeconds));
            limitRule.setIdentifierAddress(identifierAddress);
            limitRule.setIdentifierKey(identifierkey);
            limitRules.add(limitRule);
        }
        return limitRules;
    }
}
