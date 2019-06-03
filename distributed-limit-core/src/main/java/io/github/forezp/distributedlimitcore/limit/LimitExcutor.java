package io.github.forezp.distributedlimitcore.limit;



import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.entity.LimitResult;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-25
 **/

public interface LimitExcutor {

    public LimitResult tryAccess(LimitEntity limitEntity);

}
