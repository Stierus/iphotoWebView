import java.util.HashMap;

/**
 * Created by pavel on 29.05.15.
 */
public class PhotoLibrary {
    protected HashMap<String, AlbumInfo>albums;

    protected HashMap<Integer, PhotoInfo>photos;

    public PhotoLibrary(HashMap<String, AlbumInfo> albums, HashMap<Integer, PhotoInfo> photos) {
        this.albums = albums;
        this.photos = photos;
    }

    public HashMap<String, AlbumInfo> getAlbums() {
        return albums;
    }

    public void setAlbums(HashMap<String, AlbumInfo> albums) {
        this.albums = albums;
    }

    public HashMap<Integer, PhotoInfo> getPhotos() {
        return photos;
    }

    public void setPhotos(HashMap<Integer, PhotoInfo> photos) {
        this.photos = photos;
    }
}
