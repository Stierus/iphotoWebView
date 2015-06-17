package ru.stierus.iphotoweb.service.model;

import com.google.common.collect.ImmutableList;

public class AlbumInfo {

    private final Integer id;
    private final String name;
    private final String type;
    private final Integer photoCount;
    private final ImmutableList<Integer> photoIdList;

    public AlbumInfo(Integer id, String name, String type, Integer photoCount, ImmutableList<Integer> photoIdList){
        this.id = id;
        this.name = name;
        this.type = type;
        this.photoCount = photoCount;
        this.photoIdList = photoIdList;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public ImmutableList<Integer> getPhotoIdList() {
        return photoIdList;
    }

    public Integer getId() {
        return id;
    }

    public static AlbumInfoBuilder builder() {
        return new AlbumInfoBuilder();
    }

    public static class AlbumInfoBuilder {
        private Integer id;
        private Integer photoCount;
        private String name;
        private String type;
        private ImmutableList.Builder<Integer> photoIdList = ImmutableList.builder();

        public AlbumInfoBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public AlbumInfoBuilder setPhotoCount(Integer photoCount) {
            this.photoCount = photoCount;
            return this;
        }

        public AlbumInfoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AlbumInfoBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public AlbumInfoBuilder addPhotoId(Integer photoId) {
            this.photoIdList.add(photoId);
            return this;
        }

        public AlbumInfo build() {
            return new AlbumInfo(id, name, type, photoCount, photoIdList.build());
        }
    }
}
