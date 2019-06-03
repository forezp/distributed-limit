package io.github.forezp.distributedlimitcore.collector;

import com.alibaba.fastjson.JSON;
import io.github.forezp.distributedlimitcore.entity.LimitCollectData;
import io.github.forezp.distributedlimitcore.http.ApacheAsyncClientExecutor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by forezp on 2019/5/1.
 */
public class HttpLimitDataCollector extends AbstrctLimitDataCollector{

    private static final Logger LOG = LoggerFactory.getLogger(HttpLimitDataCollector.class);

    private CloseableHttpAsyncClient httpAsyncClient;

    public HttpLimitDataCollector(ApacheAsyncClientExecutor apacheAsyncClientExecutor){
        httpAsyncClient=apacheAsyncClientExecutor.getClient();
    }



    @Override
    public void reportData() {
        String reportUrl = "http://localhost:8080/recevie-data";
        Set<Map.Entry<String,LimitCollectData>> set=getCollectDataMap().entrySet();
        for (Map.Entry<String,LimitCollectData> entry:set){
            LimitCollectData limitCollectData=entry.getValue();
            String value = JSON.toJSONString(limitCollectData);
            HttpEntity entity = new StringEntity(value, "utf-8");
            HttpPost httpPost = new HttpPost(reportUrl);
            httpPost.addHeader("content-type", "application/json;charset=utf-8");
            httpPost.setEntity(entity);
            HttpAsyncCallback httpAsyncCallback = new HttpAsyncCallback();
            httpAsyncCallback.setHttpPost(httpPost);
            LOG.info("send request,url:{0},param:{1}",reportUrl,value);
            httpAsyncClient.execute(httpPost, httpAsyncCallback);
        }

    }

    public class HttpAsyncCallback implements FutureCallback<HttpResponse> {
        private HttpPost httpPost;

        public void setHttpPost(HttpPost httpPost) {
            this.httpPost = httpPost;
        }

        @Override
        public void completed(HttpResponse httpResponse) {
            LOG.info("response:"+httpResponse.getStatusLine());
            httpPost.reset();
        }

        @Override
        public void failed(Exception e) {
            httpPost.reset();
            LOG.error("Monitor web service invoke failed, url={}", httpPost.getURI(), e);
        }

        @Override
        public void cancelled() {
            httpPost.reset();
        }
    }
}
