package cloud.chat.bots.http;

import cloud.chat.bots.http.proxy.HttpConnectionSocketFactory;
import cloud.chat.bots.http.proxy.HttpSSLConnectionSocketFactory;
import cloud.chat.bots.http.proxy.SocksConnectionSocketFactory;
import cloud.chat.bots.http.proxy.SocksSSLConnectionSocketFactory;
import cloud.chat.bots.cc.DefaultBotOptions;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import java.util.concurrent.TimeUnit;

/**
 * @author gx
 */
public class BotHttpClientBuilder {

    public static CloseableHttpClient build(DefaultBotOptions options) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionManager(createConnectionManager(options))
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100);
        return httpClientBuilder.build();
    }

    private static HttpClientConnectionManager createConnectionManager(DefaultBotOptions options) {
        Registry<ConnectionSocketFactory> registry;
        switch (options.getProxyType()) {
            case NO_PROXY:
                return null;
            case HTTP:
                registry = RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("http", new HttpConnectionSocketFactory())
                        .register("https", new HttpSSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
                return new PoolingHttpClientConnectionManager(registry);
            case SOCKS4:
            case SOCKS5:
                registry = RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("http", new SocksConnectionSocketFactory())
                        .register("https", new SocksSSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                        .build();
                return new PoolingHttpClientConnectionManager(registry);
        }
        return null;
    }

}
