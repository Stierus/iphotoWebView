package ru.stierus.iphotoweb.service.model;

import com.google.common.collect.ImmutableMap;

import java.nio.file.Path;
import java.nio.file.Paths;


public class PhotoLibrary {
    private final String libraryPath;

    private final ImmutableMap<String, AlbumInfo> albums;

    private final ImmutableMap<Integer, PhotoInfo> photos;

    public PhotoLibrary(
            String path,
            ImmutableMap<String, AlbumInfo> albums,
            ImmutableMap<Integer, PhotoInfo> photos
    ) {
        this.libraryPath = path;
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
        private String libraryPath = "";
        private final ImmutableMap.Builder<String, AlbumInfo> albumsBuilder = ImmutableMap.builder();
        private final ImmutableMap.Builder<Integer, PhotoInfo> photosBuilder = ImmutableMap.builder();

        public PhotoLibraryBuilder setPath(String path) {
            libraryPath = path;
            return this;
        }

        public PhotoLibraryBuilder addAlbum(AlbumInfo album) {
            albumsBuilder.put(album.getName(), album);
            return this;
        }

        public PhotoLibraryBuilder addPhoto(PhotoInfo photo) {
            photosBuilder.put(photo.getId(), photo);
            return this;
        }

        public PhotoLibrary build() {
            return new PhotoLibrary(libraryPath, albumsBuilder.build(), photosBuilder.build());
        }
    }
}
