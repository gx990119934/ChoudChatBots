package cloud.chat.bots.common;


import cloud.chat.bots.exceptions.BotApiValidationException;

/**
 * @author gx
 * The interface implemented by the object that validates its fields
 */
public interface Validable {

    default void validate() throws BotApiValidationException {
        
    }
}
