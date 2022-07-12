package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

/**
 * 此对象表示准备下载的文件
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class File implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String FILE_SIZE_FIELD = "file_size";
    private static final String FILE_PATH_FIELD = "file_path";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Identifier for this file, which can be used to download or reuse the file
    /**
     * 此文件的唯一标识符，应该随着时间的推移和不同的机器人相同。
     * 不能用于下载或重复使用文件。
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    /**
     * 可选的。
     * 文件大小以字节为单位。
     * 它可以大于 2^31，并且某些编程语言在解释它时可能存在困难/无声的缺陷。
     * 但它最多有 52 个有效位，因此有符号的 64 位整数或双精度浮点类型对于存储此值是安全的。
     */
    @JsonProperty(FILE_SIZE_FIELD)
    private Long fileSize;
    @JsonProperty(FILE_PATH_FIELD)
    private String filePath; ///< Optional. File path. Use https://api.cloudchat.org/file/bot<token>/<file_path> to get the file.

    public String getFileUrl(String botToken) {
        return getFileUrl(botToken, filePath);
    }

    public static String getFileUrl(String botToken, String filePath) {
        if (botToken == null || botToken.isEmpty()) {
            throw new InvalidParameterException("Bot token can't be empty");
        }
        return MessageFormat.format("https://api.cloudchat.org/file/bot{0}/{1}", botToken, filePath);
    }
}
