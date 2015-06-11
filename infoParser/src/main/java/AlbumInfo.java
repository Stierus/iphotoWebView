import java.util.List;

public class AlbumInfo {

    private Integer id;
    private String name;
    private Integer type;
    private Integer photoCount;
    private List<Integer> photoIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public List<Integer> getPhotoIdList() {
        return photoIdList;
    }

    public void setPhotoIdList(List<Integer> photoIdList) {
        this.photoIdList = photoIdList;
    }

    public void addPhotoId(Integer photoId) {
        photoIdList.add(photoId);
    }
}
