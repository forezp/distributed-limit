package io.github.forezp.bootexample.interceptor;

import com.google.common.collect.Maps;
import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.exception.LimitException;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
@Component
public class WebInterceptor extends HandlerInterceptorAdapter {

    private Map<String, LimitEntity> limitEntityMap = Maps.newConcurrentMap();

    @Autowired
    LimitExcutor limitExcutor;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //限流2个维度： 用户和api维度
        //比如用户名
        String identifier = "forezp";
        //api维度
        String key = request.getRequestURI();
        String composeKey = KeyUtil.compositeKey( identifier, key );
        LimitEntity limitEntity = limitEntityMap.get( composeKey );
        if (limitEntity == null) {
            limitEntity = new LimitEntity();
            limitEntity.setIdentifier( identifier );
            limitEntity.setKey( key );
            //这可以在数据库中配置或者缓存中读取，在这里我写死
            limitEntity.setSeconds( 1 );
            limitEntity.setLimtNum( 10 );
            limitEntityMap.putIfAbsent( composeKey, limitEntity );
        }
        if (!limitExcutor.tryAccess( limitEntity )) {
            throw new LimitException( "you fail access, cause api limit rate ,try it later" );
        }

        return true;
    }


}
