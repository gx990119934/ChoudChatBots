package cloud.chat.bots.generics;

/**
 * @author gx
 * retry interface
 */
public interface BackOff {

    /**
     * should be able to start over
     */
    void reset();

    /**
     * Specify the next backoff interval in milliseconds
     */
    long nextBackOffMillis();
}
