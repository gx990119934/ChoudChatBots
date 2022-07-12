package cloud.chat.bots.cc;

import cloud.chat.common.ApiConstants;
import cloud.chat.bots.common.CcThreadPool;
import cloud.chat.bots.common.ExeRequest;
import cloud.chat.bots.common.SentCallback;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.http.BotHttpClientBuilder;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.methods.send.SendDocument;
import cloud.chat.bots.methods.send.SendPhoto;
import cloud.chat.bots.objects.InputFile;
import cloud.chat.bots.objects.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author gx
 * Implement all the methods needed to interact with the server
 */
@Slf4j
public abstract class DefaultExeRequest extends ExeRequest {
    private static final ContentType TEXT_PLAIN_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

    protected final ExecutorService exe;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DefaultBotOptions options;
    private final CloseableHttpClient httpClient;
    private final RequestConfig requestConfig;

    protected DefaultExeRequest(DefaultBotOptions options) {
        super();
        this.exe = CcThreadPool.instance();
        this.options = options;

        httpClient = BotHttpClientBuilder.build(options);
        configureHttpContext();

        final RequestConfig configFromOptions = options.getRequestConfig();
        if (configFromOptions != null) {
            this.requestConfig = configFromOptions;
        } else {
            this.requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                    .setSocketTimeout(ApiConstants.SOCKET_TIMEOUT)
                    .setConnectTimeout(ApiConstants.SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(ApiConstants.SOCKET_TIMEOUT).build();
        }
    }

    public abstract String getBotToken();

    public final DefaultBotOptions getOptions() {
        return options;
    }

    public String getBaseUrl() {
        return options.getBaseUrl() + getBotToken() + "/";
    }

    public final Message execute(SendDocument sendDocument) throws BotApiException {
        assertParamNotNull(sendDocument, "sendDocument");

        sendDocument.validate();
        try {
            String url = getBaseUrl() + SendDocument.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendDocument.CHATID_FIELD, sendDocument.getChatId(), TEXT_PLAIN_CONTENT_TYPE);

            addInputFile(builder, sendDocument.getDocument(), SendDocument.DOCUMENT_FIELD, true);

            if (sendDocument.getReplyMarkup() != null) {
                builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendDocument.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getReplyToMessageId() != null) {
                builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getCaption() != null) {
                builder.addTextBody(SendDocument.CAPTION_FIELD, sendDocument.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendDocument.getParseMode() != null) {
                    builder.addTextBody(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendDocument.getDisableNotification() != null) {
                builder.addTextBody(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getProtectContent() != null) {
                builder.addTextBody(SendDocument.PROTECTCONTENT_FIELD, sendDocument.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }

            if (sendDocument.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendDocument.ALLOWSENDINGWITHOUTREPLY_FIELD, sendDocument.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getDisableContentTypeDetection() != null) {
                builder.addTextBody(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getCaptionEntities() != null) {
                builder.addTextBody(SendDocument.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendDocument.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }

            if (sendDocument.getThumb() != null) {
                addInputFile(builder, sendDocument.getThumb(), SendDocument.THUMB_FIELD, false);
                builder.addTextBody(SendDocument.THUMB_FIELD, sendDocument.getThumb().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
            }

            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendDocument.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new BotApiException("Unable to send document", e);
        }
    }

    public final Message execute(SendPhoto sendPhoto) throws BotApiException {
        assertParamNotNull(sendPhoto, "sendPhoto");

        sendPhoto.validate();
        try {
            String url = getBaseUrl() + SendPhoto.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendPhoto.getPhoto(), SendPhoto.PHOTO_FIELD, true);

            if (sendPhoto.getReplyMarkup() != null) {
                builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendPhoto.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getReplyToMessageId() != null) {
                builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getCaption() != null) {
                builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendPhoto.getParseMode() != null) {
                    builder.addTextBody(SendPhoto.PARSEMODE_FIELD, sendPhoto.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendPhoto.getDisableNotification() != null) {
                builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendPhoto.ALLOWSENDINGWITHOUTREPLY_FIELD, sendPhoto.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getProtectContent() != null) {
                builder.addTextBody(SendPhoto.PROTECTCONTENT_FIELD, sendPhoto.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getCaptionEntities() != null) {
                builder.addTextBody(SendPhoto.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendPhoto.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendPhoto.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new BotApiException("Unable to send photo", e);
        }
    }

    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendDocument));
            } catch (BotApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendPhoto));
            } catch (BotApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }


    @Override
    protected final <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback) {
        //noinspection Convert2Lambda
        exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String responseContent = sendMethodRequest(method);
                    try {
                        callback.onResult(method, method.deserializeResponse(responseContent));
                    } catch (BotApiRequestException e) {
                        callback.onError(method, e);
                    }
                } catch (IOException | BotApiValidationException e) {
                    callback.onException(method, e);
                }

            }
        });
    }

    @Override
    protected <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> sendApiMethodAsync(Method method) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                String responseContent = sendMethodRequest(method);
                completableFuture.complete(method.deserializeResponse(responseContent));
            } catch (IOException | BotApiValidationException | BotApiRequestException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    protected final <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws BotApiException {
        try {
            String responseContent = sendMethodRequest(method);
            return method.deserializeResponse(responseContent);
        } catch (IOException e) {
            throw new BotApiException("Unable to execute " + method.getMethod() + " method", e);
        }
    }

    private void configureHttpContext() {

        if (options.getProxyType() != DefaultBotOptions.ProxyType.NO_PROXY) {
            InetSocketAddress socksaddr = new InetSocketAddress(options.getProxyHost(), options.getProxyPort());
            options.getHttpContext().setAttribute("socketAddress", socksaddr);
        }

        if (options.getProxyType() == DefaultBotOptions.ProxyType.SOCKS4) {
            options.getHttpContext().setAttribute("socksVersion", 4);
        }
        if (options.getProxyType() == DefaultBotOptions.ProxyType.SOCKS5) {
            options.getHttpContext().setAttribute("socksVersion", 5);
        }

    }

    private <T extends Serializable, Method extends BotApiMethod<T>> String sendMethodRequest(Method method) throws BotApiValidationException, IOException {
        method.validate();
        String url = getBaseUrl() + method.getMethod();
        HttpPost httppost = configuredHttpPost(url);
        httppost.addHeader("charset", StandardCharsets.UTF_8.name());
        httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(method), ContentType.APPLICATION_JSON));
        return sendHttpPostRequest(httppost);
    }

    private String sendHttpPostRequest(HttpPost httppost) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(httppost, options.getHttpContext())) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }

    private HttpPost configuredHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        return httppost;
    }


    private void addInputFile(MultipartEntityBuilder builder, InputFile file, String fileField, boolean addField) {
        if (file.isNew()) {
            if (file.getNewMediaFile() != null) {
                builder.addBinaryBody(file.getMediaName(), file.getNewMediaFile(), ContentType.APPLICATION_OCTET_STREAM, file.getMediaName());
            } else if (file.getNewMediaStream() != null) {
                builder.addBinaryBody(file.getMediaName(), file.getNewMediaStream(), ContentType.APPLICATION_OCTET_STREAM, file.getMediaName());
            }
        }

        if (addField) {
            builder.addTextBody(fileField, file.getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
        }
    }

    private void assertParamNotNull(Object param, String paramName) throws BotApiException {
        if (param == null) {
            throw new BotApiException("Parameter " + paramName + " can not be null");
        }
    }
}
