package ru.stierus.iphotoweb.service.model;

public class PhotoInfo {

    private final Integer id;
    private final String guid;
    private final String caption;
    private final String path;
    private final String thumbPath;
    private final String type;
    private final String comment;

    public PhotoInfo(
            Integer id,
            String guid,
            String caption,
            String path,
            String thumbPath,
            String type,
            String comment
    ) {
        this.id = id;
        this.guid = guid;
        this.caption = caption;
        this.path = path;
        this.thumbPath = thumbPath;
        this.type = type;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public String getCaption() {
        return caption;
    }

    public String getPath() {
        return path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public String getType() {
        return type;
    }

    public String getComment() { return comment; }

    public static PhotoInfoBuilder builder() {
        return new PhotoInfoBuilder();
    }

    public static class PhotoInfoBuilder {
        private Integer id;
        private String guid;
        private String caption;
        private String path;
        private String thumbPath;
        private String type;
        private String comment;

        public void setId(Integer id) {
            this.id = id;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setThumbPath(String thumbPath) {
            this.thumbPath = thumbPath;
        }

        public void setType(String type) { this.type = type; }

        public void setComment(String comment) { this.comment = comment; }

        public PhotoInfo build() {
            return new PhotoInfo(id, guid, caption, path, thumbPath, type, comment);
        }
    }
}
