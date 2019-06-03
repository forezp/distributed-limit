package io.github.forezp.distributedlimitcore.filter;

import io.github.forezp.distributedlimitcore.collector.LimitDataCollector;
import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.entity.LimitResult;
import io.github.forezp.distributedlimitcore.exception.LimitException;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.rule.LimitEntityBuilder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by forezp on 2019/5/1.
 */
public class RateLimitFilter extends OncePerRequestFilter {

    private LimitExcutor limitExcutor;
    private LimitEntityBuilder limitEntityBuilder;
    private LimitDataCollector limitDataCollector;

    public RateLimitFilter(LimitExcutor limitExcutor,LimitEntityBuilder limitEntityBuilder
            ,LimitDataCollector limitDataCollector){
        this.limitExcutor=limitExcutor;
        this.limitEntityBuilder=limitEntityBuilder;
        this.limitDataCollector=limitDataCollector;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        List<LimitEntity> limitEntities=limitEntityBuilder.build(httpServletRequest);
        for(LimitEntity limitEntity:limitEntities){
            LimitResult result=limitExcutor.tryAccess(limitEntity);
            limitDataCollector.collect(result);
            if(result.getResultType()!= LimitResult.ResultType.SUCCESS){
                throw new LimitException("you fail adccess,cause api limit rate,try it later");

            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
