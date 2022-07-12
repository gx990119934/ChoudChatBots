package cloud.chat.bots.generics;


import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.objects.Update;

import java.util.List;

/**
 * @author gx
 * Long poll the bot via the getUpdate method
 */
public interface LongPollingBot extends Bot {
    /**
     * This method is called when updates are received via the GetUpdates method
     *
     * @param update Update received
     */
    void onUpdateReceived(Update update);

    /**
     * This method is called when updates are received via the GetUpdates method
     * If not reimplemented - it just sends the update to {@link #onUpdateReceived(Update)}
     *
     * @param updates list of Update received
     */
    default void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }

    BotOptions getOptions();

    void clearWebhook() throws BotApiRequestException;

    /**
     * Called when the BotSession is closed
     */
    default void onClosing() {
    }
}
