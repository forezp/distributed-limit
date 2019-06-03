package io.github.forezp.distributedlimitcore.rule;

import io.github.forezp.distributedlimitcore.entity.LimitRule;

import java.util.List;

/**
 * rate.limit.enable :true
 * rate.limit.global.num: 100
 * rate.limit.
 * rate.limit:
 * Created by forezp on 2019/5/1.
 */
public interface LimitRuleLoader {

    List<LimitRule> load();
}
