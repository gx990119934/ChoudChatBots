package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * This object represents a generic file (as opposed to photo and audio files).
 *
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Document implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILENAME_FIELD = "file_name";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Identifier for this file, which can be used to download or reuse the file
    /**
     * A unique identifier for this file, which should be the same for different robots over time.
     * Cannot be used to download or reuse files.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    @JsonProperty(FILENAME_FIELD)
    private String fileName; ///< Optional. Original filename as defined by sender
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Optional. Mime type of a file as defined by sender
    /**
     * Optional.
     * The file size is in bytes.
     * It can be larger than 2^31, and some programming languages may have difficulty/silent flaws in interpreting it.
     * but it has at most 52 significant bits, so a signed 64-bit integer or double-precision floating-point type is safe to store this valu。
     */
    @JsonProperty(FILESIZE_FIELD)
    private Long fileSize;
}
