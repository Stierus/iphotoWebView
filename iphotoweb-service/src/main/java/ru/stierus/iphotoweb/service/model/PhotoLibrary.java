package ru.stierus.iphotoweb.service.model;

import com.google.common.collect.ImmutableMap;


public class PhotoLibrary {
    private final ImmutableMap<String, AlbumInfo> albums;

    private final ImmutableMap<Integer, PhotoInfo> photos;

    public PhotoLibrary(ImmutableMap<String, AlbumInfo> albums, ImmutableMap<Integer, PhotoInfo> photos) {
        this.albums = albums;
        this.photos = photos;
    }

    public ImmutableMap<String, AlbumInfo> getAlbums() {
        return albums;
    }

    public ImmutableMap<Integer, PhotoInfo> getPhotos() {
        return photos;
    }

    public static PhotoLibraryBuilder builder() {
        return new PhotoLibraryBuilder();
    }

    public static class PhotoLibraryBuilder {
        private final ImmutableMap.Builder<String, AlbumInfo> albumsBuilder = ImmutableMap.builder();
        private final ImmutableMap.Builder<Integer, PhotoInfo> photosBuilder = ImmutableMap.builder();

        public PhotoLibraryBuilder addAlbum(AlbumInfo album) {
            albumsBuilder.put(album.getName(), album);
            return this;
        }

        public PhotoLibraryBuilder addPhoto(PhotoInfo photo) {
            photosBuilder.put(photo.getId(), photo);
            return this;
        }

        public PhotoLibrary build() {
            return new PhotoLibrary(albumsBuilder.build(), photosBuilder.build());
        }
    }
}
