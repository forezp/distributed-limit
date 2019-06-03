package io.github.forezp.distributedlimitcore.http;


import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

import static io.github.forezp.distributedlimitcore.constant.ConfigConstant.*;

/**
 * Created by forezp on 2018/6/2.
 */

public class ApacheAsyncClientExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(ApacheAsyncClientExecutor.class);

    private CloseableHttpAsyncClient httpAsyncClient;
    public static final int CPUS = Math.max( 2, Runtime.getRuntime().availableProcessors() );

    private boolean isStarted=false;
    public void initialize() throws Exception {

        final CyclicBarrier barrier = new CyclicBarrier(2);
        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {

                    IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                            .setIoThreadCount(CPUS * 2)
                            .setConnectTimeout(Integer.parseInt(HTTPCLIENT_CONNCT_TIMEOUT_DEFAULT))
                            .setSoTimeout(Integer.parseInt(HTTPCLIENT_SOCKET_TIMEOUT_DEFAULT))
                            .setSndBufSize(Integer.parseInt(HTTPCLIENT_SEDBUFSIZE_DEFAULT))
                            .setRcvBufSize(Integer.parseInt(HTTPCLIENT_RCV_BUFSIZE_DEFAULT))
                            .setBacklogSize(Integer.parseInt(HTTPCLIENT_BACK_LOG_SIZE_DEFAULT))
                            .setTcpNoDelay(true)
                            .setSoReuseAddress(true)
                            .setSoKeepAlive(true)
                            .build();
                    ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
                    PoolingNHttpClientConnectionManager httpManager = new PoolingNHttpClientConnectionManager(ioReactor);
                    httpManager.setMaxTotal(Integer.parseInt(HTTPCLIENT_MAX_TOTAL_DEFAULT));
                    httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(httpManager).build();
                    httpAsyncClient.start();

                    LOG.info("Create apache async client successfully");
                    isStarted=true;
                    barrier.await();
                } catch (IOReactorException e) {
                    LOG.error("Create apache async client failed", e);
                }

                return null;
            }
        });

        barrier.await();
    }

    public CloseableHttpAsyncClient getClient() {
        return httpAsyncClient;
    }

    public boolean isStarted() {
        return isStarted;
    }
}