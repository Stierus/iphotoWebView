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
        PhotoLibrary.PhotoLibraryBuilder photoLibraryBuilder = PhotoLibrary.builder();

        try {
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
            NSObject[] albumList = ((NSArray)rootDict.objectForKey("List of Albums")).getArray();

            for (int i = 0; i < albumList.length; i++) {
                NSDictionary album = (NSDictionary) albumList[i];
                AlbumInfo.AlbumInfoBuilder albumBuilder = AlbumInfo.builder();

                albumBuilder.setId(Integer.parseInt(album.objectForKey("AlbumId").toString()));
                albumBuilder.setName(album.objectForKey("AlbumName").toString());
                albumBuilder.setPhotoCount(Integer.parseInt(album.objectForKey("PhotoCount").toString()));
                albumBuilder.setType(album.objectForKey("Album Type").toString());
                NSObject[] keyList = ((NSArray)album.objectForKey("KeyList")).getArray();
                for (int j = 0; j < keyList.length; j++) {
                    //@TODO check nsNumber
                    Integer photoId = Integer.parseInt(keyList[j].toString());
                    albumBuilder.addPhotoId(photoId);
                }
                photoLibraryBuilder.addAlbum(albumBuilder.build());
            }

            HashMap<String, NSObject> photoList = ((NSDictionary)rootDict.objectForKey("Master Image List")).getHashMap();
            Iterator photoIterator = photoList.entrySet().iterator();
            while (photoIterator.hasNext()) {
                Map.Entry photoSet = (Map.Entry) photoIterator.next();
                NSDictionary image = (NSDictionary) photoSet.getValue();
                PhotoInfo.PhotoInfoBuilder photoInfoBuilder = PhotoInfo.builder();
                photoInfoBuilder.setCaption(image.objectForKey("Caption").toString());
                photoInfoBuilder.setGuid(image.objectForKey("GUID").toString());
                photoInfoBuilder.setPath(image.objectForKey("ImagePath").toString());
                photoInfoBuilder.setThumbPath(image.objectForKey("ThumbPath").toString());
                photoInfoBuilder.setType(image.objectForKey("MediaType").toString());
                photoInfoBuilder.setId(Integer.parseInt(photoSet.getKey().toString()));
                photoLibraryBuilder.addPhoto(photoInfoBuilder.build());

            }
            return photoLibraryBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IPhotoLibraryParserException("parse error", e);
        }
    }
}
