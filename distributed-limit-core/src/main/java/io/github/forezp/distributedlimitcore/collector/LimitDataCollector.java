package io.github.forezp.distributedlimitcore.collector;

import io.github.forezp.distributedlimitcore.entity.LimitResult;

/**
 * Created by forezp on 2019/5/1.
 */
public interface LimitDataCollector {

     void collect(LimitResult result);

     void reportData();

}
