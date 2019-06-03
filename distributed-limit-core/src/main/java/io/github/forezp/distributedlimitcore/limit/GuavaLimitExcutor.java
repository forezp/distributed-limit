package io.github.forezp.distributedlimitcore.limit;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.entity.LimitResult;
import io.github.forezp.distributedlimitcore.util.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

import java.util.concurrent.TimeUnit;

/**
 * 采用RateLimiter 令牌桶的算法
 * <p>
 * Email miles02@163.com
 *
 * @author fangzhipeng
 *         create 2018-06-26
 **/
public class GuavaLimitExcutor implements LimitExcutor {

    Logger log = LoggerFactory.getLogger(GuavaLimitExcutor.class);

    private Map<String, RateLimiter> rateLimiterMap = Maps.newConcurrentMap();


    @Override
    public LimitResult tryAccess(LimitEntity limitEntity) {

        RateLimiter rateLimiter = getRateLimiter(limitEntity);
        if (rateLimiter == null) {
            return null;
        }
        LimitResult limitResult = new LimitResult();
        limitResult.setUrl(limitEntity.getKey());
        limitEntity.setIdentifier(limitEntity.getIdentifier());
        boolean access = rateLimiter.tryAcquire(1, 2000, TimeUnit.MILLISECONDS);
        log.info("identifier:" + limitEntity.getIdentifier() + " url:" + limitResult.getUrl() + " access:{}", access);
        if (access) {
            limitResult.setResultType(LimitResult.ResultType.SUCCESS);
        } else {
            limitResult.setResultType(LimitResult.ResultType.FAIL);
        }
        return limitResult;
    }

    private RateLimiter getRateLimiter(LimitEntity limitEntity) {
        if (limitEntity == null) {
            return null;
        }
        String key =KeyUtil.getKey(limitEntity);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        RateLimiter rateLimiter = rateLimiterMap.get(key);
        Double limitNum = Double.valueOf(String.valueOf(limitEntity.getLimtNum()));
        Double permitsPerSecond = limitNum / limitEntity.getSeconds();
        if (rateLimiter == null) {
            RateLimiter newRateLimiter = RateLimiter.create(permitsPerSecond);
            rateLimiter = rateLimiterMap.putIfAbsent(key, newRateLimiter);
            if (rateLimiter == null) {
                rateLimiter = newRateLimiter;
            }
        }else {
            if(rateLimiter.getRate()!=permitsPerSecond){
                RateLimiter newRateLimiter = RateLimiter.create(permitsPerSecond);
                rateLimiter = rateLimiterMap.put(key, newRateLimiter);
                if (rateLimiter == null) {
                    rateLimiter = newRateLimiter;
                }
            }
        }
        return rateLimiter;

    }


//    public static void main(String[] args) {
//        RateLimiter rateLimiter = RateLimiter.create( 0.5 );
//        CyclicBarrier barrier = new CyclicBarrier( 2 );
//        new Thread( new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    System.out.println( "访问次数：" + i );
//                    try {
//                        Thread.sleep( 1000 );
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    boolean access = rateLimiter.tryAcquire( 1 );
//                    System.out.println( "访问次数：" + i + " acess: " + access );
//                }
//                try {
//                    barrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            }
//        } ).start();
//        try {
//            barrier.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (BrokenBarrierException e) {
//            e.printStackTrace();
//        }
//        System.out.println( "test end" );
//    }
}
