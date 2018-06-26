package io.github.forezp.distributedlimitcore.config.condition;

import io.github.forezp.distributedlimitcore.constant.ConfigConstant;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
public class GuavaLimitCondition extends EqualCondition {
    public GuavaLimitCondition() {
        super( ConfigConstant.LIMIT_TYPE, ConfigConstant.LIMIT_TYPE_LOCAL );
    }
}
