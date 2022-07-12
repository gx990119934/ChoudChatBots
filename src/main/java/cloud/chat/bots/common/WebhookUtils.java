package cloud.chat.bots.common;

import cloud.chat.bots.cc.DefaultExeRequest;
import cloud.chat.bots.cc.DefaultBotOptions;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.http.BotHttpClientBuilder;
import cloud.chat.bots.generics.WebhookBot;
import cloud.chat.bots.methods.updates.DeleteWebhook;
import cloud.chat.bots.methods.updates.SetWebhook;
import cloud.chat.bots.objects.InputFile;
import cloud.chat.common.ApiConstants;
import com.google.common.base.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * @author gx
 */
public final class WebhookUtils {
  private static final ContentType TEXT_PLAIN_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

  private WebhookUtils() {

  }

  /**
   * 设置 webhook 地址以接收更新
   * @param bot 将 webhook 设置为
   * @param setWebhook SetSebhook 对象与 webhook 信息
   * @throws BotApiRequestException 如果执行请求有任何问题
   *
   * @apiNote API 参数将仅取自 SetWebhook 对象
   * @apiNote Bot 选项将从 Bot 获取以设置 HTTP 连接
   */
  public static void setWebhook(DefaultExeRequest bot, WebhookBot webhookBot, SetWebhook setWebhook) throws BotApiException {
    setWebhook.validate();

    DefaultBotOptions botOptions = bot.getOptions();
    try (CloseableHttpClient httpclient = BotHttpClientBuilder.build(botOptions)) {
      String requestUrl = bot.getBaseUrl() + SetWebhook.PATH;

      RequestConfig requestConfig = botOptions.getRequestConfig();
      if (requestConfig == null) {
        requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                .setSocketTimeout(ApiConstants.SOCKET_TIMEOUT)
                .setConnectTimeout(ApiConstants.SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(ApiConstants.SOCKET_TIMEOUT).build();
      }

      HttpPost httppost = new HttpPost(requestUrl);
      httppost.setConfig(requestConfig);
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.addTextBody(SetWebhook.URL_FIELD, getBotUrl(setWebhook, webhookBot), TEXT_PLAIN_CONTENT_TYPE);
      if (setWebhook.getMaxConnections() != null) {
        builder.addTextBody(SetWebhook.MAXCONNECTIONS_FIELD, setWebhook.getMaxConnections().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (setWebhook.getDropPendingUpdates() != null) {
        builder.addTextBody(SetWebhook.DROPPENDINGUPDATES_FIELD, setWebhook.getDropPendingUpdates().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (setWebhook.getCertificate() != null) {
        InputFile webhookFile = setWebhook.getCertificate();
        if (webhookFile.getNewMediaFile() != null) {
          builder.addBinaryBody(SetWebhook.CERTIFICATE_FIELD, webhookFile.getNewMediaFile(), ContentType.TEXT_PLAIN, webhookFile.getMediaName());
        } else if (webhookFile.getNewMediaStream() != null) {
          builder.addBinaryBody(SetWebhook.CERTIFICATE_FIELD, webhookFile.getNewMediaStream(), ContentType.TEXT_PLAIN, webhookFile.getMediaName());
        }
      }

      HttpEntity multipart = builder.build();
      httppost.setEntity(multipart);
      try (CloseableHttpResponse response = httpclient.execute(httppost, botOptions.getHttpContext())) {
        String responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        Boolean result = setWebhook.deserializeResponse(responseContent);
        if (!result) {
          throw new BotApiRequestException("Error setting webhook:" + responseContent);
        }
      }
    } catch (IOException e) {
      throw new BotApiRequestException("Error executing setWebook method", e);
    }
  }

  public static void clearWebhook(DefaultExeRequest bot) throws BotApiRequestException {
    try {
      String result = bot.execute(new DeleteWebhook());
      if (!result.equals("Success!")) {
        throw new BotApiRequestException("Error removing old webhook");
      }
    } catch (BotApiException e) {
      throw new BotApiRequestException("Error removing old webhook", e);
    }
  }

  private static String getBotUrl(SetWebhook setWebhook, WebhookBot webhookBot) {
    String externalUrl = setWebhook.getUrl();
    return getBotUrl(externalUrl, webhookBot.getBotPath());
  }

  private static String getBotUrl(String externalUrl, String botPath) {
    if (!externalUrl.endsWith("/")) {
      externalUrl += "/";
    }
    externalUrl += ApiConstants.WEBHOOK_URL_PATH;
    if (!Strings.isNullOrEmpty(botPath)) {
      if (!botPath.startsWith("/")) {
        externalUrl += "/";
      }
      externalUrl += botPath;
    }

    return externalUrl;
  }
}
