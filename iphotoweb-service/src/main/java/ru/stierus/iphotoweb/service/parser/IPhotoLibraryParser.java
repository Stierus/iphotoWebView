package ru.stierus.iphotoweb.service.parser;

import com.dd.plist.*;
import ru.stierus.iphotoweb.service.model.AlbumInfo;
import ru.stierus.iphotoweb.service.model.PhotoInfo;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;

import java.io.File;
import java.util.*;


public class IPhotoLibraryParser
{
    /**
     * Парсит /AlbumData.xml
     */
    public PhotoLibrary parseAlbumData(File file) throws IPhotoLibraryParserException {
        PhotoLibrary.PhotoLibraryBuilder builder = PhotoLibrary.builder();

        try {
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
            NSObject[] albumList = ((NSArray)rootDict.objectForKey("List of Albums")).getArray();

            for (int i = 0; i < albumList.length; i++) {
                NSDictionary album = (NSDictionary) albumList[i];
                AlbumInfo albumInfo = new AlbumInfo();
                albumInfo.setId(Integer.parseInt(album.objectForKey("AlbumId").toString()));
                albumInfo.setName(album.objectForKey("AlbumName").toString());
                albumInfo.setPhotoCount(Integer.parseInt(album.objectForKey("PhotoCount").toString()));
                albumInfo.setType(album.objectForKey("Album Type").toString());
                NSObject[] keyList = ((NSArray)album.objectForKey("KeyList")).getArray();
                for (int j = 0; j < keyList.length; j++) {
                    //@TODO check nsNumber
                    Integer photoId = Integer.parseInt(keyList[j].toString());
                    albumInfo.addPhotoId(photoId);
                }
                builder.addAlbum(albumInfo);
            }

            HashMap<String, NSObject> photoList = ((NSDictionary)rootDict.objectForKey("Master Image List")).getHashMap();
            Iterator photoIterator = photoList.entrySet().iterator();
            while (photoIterator.hasNext()) {
                Map.Entry photoSet = (Map.Entry) photoIterator.next();

                NSDictionary image = (NSDictionary) photoSet.getValue();
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.setCaption(image.objectForKey("Caption").toString());
                photoInfo.setGuid(image.objectForKey("GUID").toString());
                photoInfo.setPath(image.objectForKey("ImagePath").toString());
                photoInfo.setThumbPath(image.objectForKey("ThumbPath").toString());
                photoInfo.setType(image.objectForKey("MediaType").toString());
                photoInfo.setId(Integer.parseInt(photoSet.getKey().toString()));
                builder.addPhoto(photoInfo);

            }
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IPhotoLibraryParserException("parse error", e);
        }
    }
}
