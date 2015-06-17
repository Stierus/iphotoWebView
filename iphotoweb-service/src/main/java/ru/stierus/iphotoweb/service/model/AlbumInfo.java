package ru.stierus.iphotoweb.service.model;

import java.util.ArrayList;
import java.util.List;

//@TODO сделать immutable
public class AlbumInfo {

    private Integer id;
    private String name;
    private String type;
    private Integer photoCount;
    private List<Integer> photoIdList = new ArrayList<Integer>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public void setPhotoIdList(ArrayList<Integer> photoIdList) {
        this.photoIdList = photoIdList;
    }

    public void addPhotoId(Integer photoId) {
        this.photoIdList.add(photoId);
    }


    
}
