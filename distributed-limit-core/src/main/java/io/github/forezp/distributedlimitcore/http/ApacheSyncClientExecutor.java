package io.github.forezp.distributedlimitcore.http;



import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static io.github.forezp.distributedlimitcore.constant.ConfigConstant.HTTPCLIENT_CONNCT_REQUEST_TIMEOUT_DEFAULT;
import static io.github.forezp.distributedlimitcore.constant.ConfigConstant.HTTPCLIENT_CONNCT_TIMEOUT_DEFAULT;
import static io.github.forezp.distributedlimitcore.constant.ConfigConstant.HTTPCLIENT_SOCKET_TIMEOUT_DEFAULT;

/**
 * Created by forezp on 2018/6/2.
 */


public class ApacheSyncClientExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(ApacheSyncClientExecutor.class);

    private CloseableHttpClient httpSyncClient;



    public void initialize(boolean https) throws Exception {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(HTTPCLIENT_CONNCT_TIMEOUT_DEFAULT))
                .setConnectionRequestTimeout(Integer.parseInt(HTTPCLIENT_CONNCT_REQUEST_TIMEOUT_DEFAULT))
                .setSocketTimeout(Integer.parseInt(HTTPCLIENT_SOCKET_TIMEOUT_DEFAULT))
                .build();

        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setDefaultRequestConfig(requestConfig);

        if (https) {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }

            }).build();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
        }

        httpSyncClient = clientBuilder.build();

        LOG.info("Create apache sync client with {} successfully", https ? "https mode" : "http mode");
    }

    public CloseableHttpClient getClient() {
        return httpSyncClient;
    }

}