import java.util.HashMap;

/**
 * Created by pavel on 29.05.15.
 */
public class photoLibrary {
    protected HashMap<String, AlbumInfo>albums;

    protected HashMap<Integer, photoInfo>photos;

    public photoLibrary(HashMap<String, AlbumInfo> albums, HashMap<Integer, photoInfo> photos) {
        this.albums = albums;
        this.photos = photos;
    }

    public HashMap<String, AlbumInfo> getAlbums() {
        return albums;
    }

    public void setAlbums(HashMap<String, AlbumInfo> albums) {
        this.albums = albums;
    }

    public HashMap<Integer, photoInfo> getPhotos() {
        return photos;
    }

    public void setPhotos(HashMap<Integer, photoInfo> photos) {
        this.photos = photos;
    }
}
