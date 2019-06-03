package io.github.forezp.distributedlimitcore.collector;

import io.github.forezp.distributedlimitcore.entity.LimitCollectData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;


/**
 * Created by forezp on 2019/5/1.
 * @EnableScheduling注解无效，需要在Spring boot启动文件加
 */
@EnableScheduling
public class ConsolLimitDataCollector extends AbstrctLimitDataCollector {

    Logger logger= LoggerFactory.getLogger(ConsolLimitDataCollector.class);

    @Override
    public void reportData() {
        Set<Map.Entry<String,LimitCollectData>> set=getCollectDataMap().entrySet();
        for (Map.Entry<String,LimitCollectData> entry:set){
            String url=entry.getKey();
            LimitCollectData limitCollectData=entry.getValue();
            logger.info("url:"+url+" acessNum:"+limitCollectData.getAccess()
                    +" refuseNum:"+limitCollectData.getRefuse());
            logger.info("globalAccessNum:"+getGlobalAcess().get()+" globalRefuseNum:"+getGlobalRefuse().get());
            //limitCollectData.setAccess(0);
            //limitCollectData.setRefuse(0);
        }
    }




}
