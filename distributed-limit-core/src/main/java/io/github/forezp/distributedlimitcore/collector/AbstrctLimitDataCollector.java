package io.github.forezp.distributedlimitcore.collector;

import io.github.forezp.distributedlimitcore.entity.LimitCollectData;
import io.github.forezp.distributedlimitcore.entity.LimitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by forezp on 2019/5/1.
 * @EnableScheduling注解无效，需要在Spring boot启动文件加
 */
public abstract class AbstrctLimitDataCollector implements LimitDataCollector {


    private AtomicInteger globalAcess=new AtomicInteger(0);
    private AtomicInteger globalRefuse=new AtomicInteger(0);

    private ConcurrentHashMap<String,LimitCollectData> collectDataMap=new ConcurrentHashMap<>();


    @Override
    public void collect(LimitResult result) {
        LimitCollectData limitCollectData=collectDataMap.get(result.getUrl());
        if(limitCollectData==null){
            limitCollectData=new LimitCollectData();
            collectDataMap.put(result.getUrl(),limitCollectData);

        }
        switch (result.getResultType()){
            case FAIL:
                globalRefuse.incrementAndGet();
                limitCollectData.setRefuse(limitCollectData.getRefuse()+1);
                break;
            case SUCCESS:
                globalAcess.incrementAndGet();
                limitCollectData.setAccess(limitCollectData.getAccess()+1);
                break;
        }

    }

    @Scheduled(fixedRateString = "${limit.data.collect.period:5000}")
    public void reportCollectData() {
      reportData();
    }

    public AtomicInteger getGlobalAcess() {
        return globalAcess;
    }

    public void setGlobalAcess(AtomicInteger globalAcess) {
        this.globalAcess = globalAcess;
    }

    public AtomicInteger getGlobalRefuse() {
        return globalRefuse;
    }

    public void setGlobalRefuse(AtomicInteger globalRefuse) {
        this.globalRefuse = globalRefuse;
    }

    public ConcurrentHashMap<String, LimitCollectData> getCollectDataMap() {
        return collectDataMap;
    }

    public void setCollectDataMap(ConcurrentHashMap<String, LimitCollectData> collectDataMap) {
        this.collectDataMap = collectDataMap;
    }
}
