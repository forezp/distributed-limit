package io.github.forezp.distributedlimitcore.limit;


import io.github.forezp.distributedlimitcore.entity.LimitEntity;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-25
 **/

public interface LimitExcutor {

    public boolean tryAccess(LimitEntity limitEntity);

}
