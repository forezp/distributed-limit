package io.github.forezp.distributedlimitcore.rule;

import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.entity.LimitRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by forezp on 2019/5/1.
 */
public class LimitEbtityBuilderImpl implements LimitEntityBuilder {

    Logger logger = LoggerFactory.getLogger(LimitEbtityBuilderImpl.class);

    private LimitRuleLoader loader;

    public LimitEbtityBuilderImpl(LimitRuleLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<LimitEntity> build(HttpServletRequest httpServletRequest) {
        Long statrBulidTime = System.currentTimeMillis();
        List<LimitEntity> limitEntities = new ArrayList<>();
        List<LimitRule> limitRules = loader.load();
        String key = httpServletRequest.getRequestURI();
        for (LimitRule limitRule : limitRules) {

            LimitEntity entity = new LimitEntity();
            BeanUtils.copyProperties(limitRule, entity);
            entity.setLimtNum(limitRule.getLimitNum());
            entity.setKey(key);
            if (!StringUtils.isEmpty(limitRule.getIdentifierKey())) {
                String identifier = httpServletRequest.getHeader(limitRule.getIdentifierKey());
                if (StringUtils.isEmpty(identifier)) {
                    identifier = httpServletRequest.getParameter(limitRule.getIdentifierKey());
                }
                entity.setIdentifier(identifier);
            }
            limitEntities.add(entity);
        }
        logger.info("build limit entity takes {0} ms",
                (System.currentTimeMillis() - statrBulidTime));
        return limitEntities;
    }
}
