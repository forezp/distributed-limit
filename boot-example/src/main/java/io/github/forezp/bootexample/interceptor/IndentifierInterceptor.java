package io.github.forezp.bootexample.interceptor;


import io.github.forezp.distributedlimitcore.util.IdentifierThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/

@Component
public class IndentifierInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户的信息，比如解析Token获取用户名，
        // 这么做主要是在基于@Limit注解在Controller的时候，能都动态设置identifier信息
        // 从而以用户维度进行限流
        String identifier = "forezp";
        IdentifierThreadLocal.set( identifier );
        return true;
    }


}
