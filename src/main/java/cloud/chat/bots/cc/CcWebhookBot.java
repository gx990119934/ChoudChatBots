package cloud.chat.bots.cc;

import cloud.chat.bots.common.WebhookUtils;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.generics.WebhookBot;
import cloud.chat.bots.methods.updates.SetWebhook;

/**
 * @author gx
 */
public abstract class CcWebhookBot extends DefaultExeRequest implements WebhookBot {

  public CcWebhookBot() {
    this(new DefaultBotOptions());
  }

  public CcWebhookBot(DefaultBotOptions options) {
    super(options);
  }

  @Override
  public void setWebhook(SetWebhook setWebhook) throws BotApiException {
    WebhookUtils.setWebhook(this, this, setWebhook);
  }
}