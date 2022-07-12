package cloud.chat.bots.objects;

import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.common.BotApiObject;
import cloud.chat.bots.common.Validable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.io.InputStream;

/**
 * @author gx
 * Input file for uploading the file to the server and using it later
 */
@JsonSerialize(using = InputFileSerializer.class, as = String.class)
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InputFile implements Validable, BotApiObject {

    private String attachName;

    /**
     * Name of the media to upload
     */
    @JsonIgnore
    private String mediaName;

    /**
     * New media file
     */
    @JsonIgnore
    private File newMediaFile;

    /**
     * New media stream
     */
    @JsonIgnore
    private InputStream newMediaStream;

    /**
     * True if the file is new, false if it is a file_id
     */
    @JsonIgnore
    private boolean isNew;

    public InputFile(String attachName) {
        this();
        setMedia(attachName);
    }

    /**
     * Constructor to set a new file
     *
     * @param mediaFile File to send
     */
    public InputFile(File mediaFile) {
        this();
        setMedia(mediaFile, mediaFile.getName());
    }

    /**
     * Constructor to set a new file
     *
     * @param mediaFile File to send
     * @param fileName Name of the file
     */
    public InputFile(File mediaFile, String fileName) {
        this();
        setMedia(mediaFile, fileName);
    }

    /**
     * Constructor to set a new file as stream
     *
     * @param mediaStream File to send
     * @param fileName Name of the file
     */
    public InputFile(InputStream mediaStream, String fileName) {
        this();
        setMedia(mediaStream, fileName);
    }

    /**
     * Use this setter to send new file.
     * @param mediaFile File to send
     * @param fileName Name of the file
     * @return This object
     */
    public InputFile setMedia(File mediaFile, String fileName) {
        this.newMediaFile = mediaFile;
        this.mediaName = fileName;
        this.attachName = "attach://" + fileName;
        this.isNew = true;
        return this;
    }

    /**
     * Use this setter to send new file.
     * @param mediaFile File to send
     * @return This object
     */
    public InputFile setMedia(File mediaFile) {
        this.newMediaFile = mediaFile;
        this.mediaName = mediaFile.getName();
        this.attachName = "attach://" + mediaFile.getName();
        this.isNew = true;
        return this;
    }

    /**
     * Use this setter to send new file as stream.
     * @param mediaStream File to send
     * @param fileName Name of the file
     * @return This object
     */
    public InputFile setMedia(InputStream mediaStream, String fileName) {
        this.newMediaStream = mediaStream;
        this.mediaName = fileName;
        this.attachName = "attach://" + fileName;
        this.isNew = true;
        return this;
    }

    public InputFile setMedia(String attachName) {
        this.attachName = attachName;
        this.isNew = false;
        return this;
    }

    public String getAttachName() {
        return attachName;
    }

    public String getMediaName() {
        return mediaName;
    }

    public File getNewMediaFile() {
        return newMediaFile;
    }

    public InputStream getNewMediaStream() {
        return newMediaStream;
    }

    public boolean isNew() {
        return isNew;
    }

    @Override
    public void validate() throws BotApiValidationException {
        if (isNew) {
            if (mediaName == null || mediaName.isEmpty()) {
                throw new BotApiValidationException("Media name can't be empty", this);
            }
            if (newMediaFile == null && newMediaStream == null) {
                throw new BotApiValidationException("Media can't be empty", this);
            }
        } else {
            if (attachName == null || attachName.isEmpty()) {
                throw new BotApiValidationException("File_id can't be empty", this);
            }
        }
    }
}
