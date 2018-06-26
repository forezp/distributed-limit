[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/forezp/DistributedLimit/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.forezp/distributed-limit-core.svg?label=maven%20central)](http://mvnrepository.com/artifact/io.github.forezp/distributed-limit-core)

## 这个项目干嘛的?

这个项目是一个Api限流的解决方案，采用的是令牌桶的方式。如果使用的Redis则是分布式限流，如果采用guava的LimitRater，则是本地限流。
分2给维度限流，一个是用户维度，一个Api维度，读者可自定义。
仅支持Spring Boot项目。


## 怎么使用？

在boot-example工程，有完整的案例。可在Controller上限流，也可以在Spring Mvc的Interceptor或者Servlet的Filter上限流。

### 开启限流

在pom文件加上jar包：

```$xslt
<dependency>
  <groupId>io.github.forezp</groupId>
  <artifactId>distributed-limit-core</artifactId>
  <version>1.0.1</version>
</dependency>

```

有2中方式，本地限流，只需要配置limit.type=local；采用Reis限流，配置limit.type=redis，以及redis的配置，如下：

```$xslt

#limit.type: local
limit.type: redis

spring:
  redis:
    host: localhost
    port: 6379
#    password: ee
    database: 1
    pool:
      max-active: 8
      max-wait: -1
      max-idle: 500
      min-idle: 0
    timeout: 0
```

## Controller上使用

在Controller上加注解，其中identifier为识别身份的，key为限流的key,limtNum为限制的次数，seconds为多少秒，后2个配置的作用是在多少秒最大的请求次数


```$xslt
@RestController
public class TestController {

    @GetMapping("/test")
    @Limit(identifier = "forezp", key = "test", limtNum = 10, seconds = 1)
    public String Test() {
        return "11";
    }
}

```

仅次操作就可以限流了。

另外如果是以注解的形式进行限流，如果以identifier即请求用户维度去限流，可以动态的设置的identifier的值，示例如下：

```$xslt

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

```

## 在Interceptor上使用

直接贴代码了，比较简单。

```$xslt
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

```

注册一下：

```$xslt
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    IndentifierInterceptor indentifierInterceptor;

    @Autowired
    WebInterceptor webInterceptor;

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( indentifierInterceptor );
        registry.addInterceptor( webInterceptor );
    }

}

```

## 联系我

如果有任何问题，可以联系我，miles02@163.com


## 后续计划

- @Limit注解支持spel表达式

