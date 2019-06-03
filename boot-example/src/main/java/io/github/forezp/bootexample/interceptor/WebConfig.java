//package io.github.forezp.bootexample.interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * Email miles02@163.com
// *
// * @author fangzhipeng
// * create 2018-06-26
// **/
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    @Autowired
//    IndentifierInterceptor indentifierInterceptor;
//
//    @Autowired
//    WebInterceptor webInterceptor;
//
//    /**
//     * 注册 拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor( indentifierInterceptor );
//        registry.addInterceptor( webInterceptor );
//    }
//
//}
