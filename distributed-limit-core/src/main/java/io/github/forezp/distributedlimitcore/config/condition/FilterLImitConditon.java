package io.github.forezp.distributedlimitcore.config.condition;

import io.github.forezp.distributedlimitcore.constant.ConfigConstant;

/**
 * Created by forezp on 2019/5/1.
 */
public class FilterLImitConditon extends EqualCondition {
    public FilterLImitConditon() {
        super( ConfigConstant.LIMIT_FILTER, ConfigConstant.LIMIT_FILTER_TRUE );
    }

}
