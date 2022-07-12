package cloud.chat.bots.cc;

import cloud.chat.common.ApiConstants;
import cloud.chat.bots.generics.BackOff;
import cloud.chat.bots.generics.BotOptions;
import lombok.Data;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import java.util.List;

/**
 * @author gx
 */
@Data
public class DefaultBotOptions implements BotOptions {
    /**
     * Maximum number of threads to use for asynchronous method execution (default 3)
     */
    private int maxThreads;
    private RequestConfig requestConfig;
    private volatile HttpContext httpContext;
    private BackOff backOff;
    private Integer maxWebhookConnections;
    private String baseUrl;
    private List<String> allowedUpdates;
    private ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
    private int getUpdatesTimeout;
    private int getUpdatesLimit;

    public enum ProxyType {
        NO_PROXY,
        HTTP,
        SOCKS4,
        SOCKS5
    }

    public DefaultBotOptions() {
        maxThreads = 3;
        baseUrl = ApiConstants.BASE_URL;
        httpContext = HttpClientContext.create();
        proxyType = ProxyType.NO_PROXY;
        getUpdatesTimeout = ApiConstants.GETUPDATES_TIMEOUT;
        getUpdatesLimit = 100;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

}
