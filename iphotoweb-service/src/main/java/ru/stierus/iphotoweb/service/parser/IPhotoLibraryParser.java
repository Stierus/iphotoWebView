package ru.stierus.iphotoweb.service.parser;

import com.dd.plist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public PhotoLibrary parseAlbumData(File file) throws PhotoLibraryParserException {
        try {
            Logger logger = LoggerFactory.getLogger(IPhotoLibraryParser.class);
            logger.info("Начало разбора файла " + file.toString());
            PhotoLibrary.PhotoLibraryBuilder photoLibraryBuilder = PhotoLibrary.builder();
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
            NSObject[] albumList = ((NSArray)rootDict.objectForKey("List of Albums")).getArray();
            String libraryPath = rootDict.objectForKey("Archive Path").toString();
            for (int i = 0; i < albumList.length; i++) {
                logger.info("i: " + i);
                NSDictionary album = (NSDictionary) albumList[i];
                AlbumInfo.AlbumInfoBuilder albumBuilder = AlbumInfo.builder();

                albumBuilder.setId(Integer.parseInt(album.objectForKey("AlbumId").toString()));
                albumBuilder.setName(album.objectForKey("AlbumName").toString());
                albumBuilder.setPhotoCount(Integer.parseInt(album.objectForKey("PhotoCount").toString()));
                albumBuilder.setType(album.objectForKey("Album Type").toString());
                NSObject[] keyList = ((NSArray)album.objectForKey("KeyList")).getArray();
                for (int j = 0; j < keyList.length; j++) {
                    logger.info("i: " + i + ", j: " + j);
                    //@TODO check nsNumber
                    Integer photoId = Integer.parseInt(keyList[j].toString());
                    albumBuilder.addPhotoId(photoId);
                }
                photoLibraryBuilder.addAlbum(albumBuilder.build());
            }

            photoLibraryBuilder.setPath(rootDict.objectForKey("Archive Path").toString());

            HashMap<String, NSObject> photoList = ((NSDictionary)rootDict.objectForKey("Master Image List")).getHashMap();
            Iterator photoIterator = photoList.entrySet().iterator();
            int k = 0;
            while (photoIterator.hasNext()) {
                logger.info("k: " + k);
                Map.Entry photoSet = (Map.Entry) photoIterator.next();
                NSDictionary image = (NSDictionary) photoSet.getValue();
                PhotoInfo.PhotoInfoBuilder photoInfoBuilder = PhotoInfo.builder();
                photoInfoBuilder.setCaption(image.objectForKey("Caption").toString());
                photoInfoBuilder.setGuid(image.objectForKey("GUID").toString());
                photoInfoBuilder.setPath(getRealPhotoPath(
                        file,
                        libraryPath,
                        image.objectForKey("ImagePath").toString()
                ));
                photoInfoBuilder.setThumbPath(getRealPhotoPath(
                        file,
                        libraryPath,
                        image.objectForKey("ThumbPath").toString()
                ));
                photoInfoBuilder.setType(image.objectForKey("MediaType").toString());
                photoInfoBuilder.setComment(image.objectForKey("Comment").toString());
                photoInfoBuilder.setId(Integer.parseInt(photoSet.getKey().toString()));
                photoLibraryBuilder.addPhoto(photoInfoBuilder.build());
                k++;
            }
            logger.info("Файл " + file.toString() + " успешно распарсен");
            return photoLibraryBuilder.build();
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(IPhotoLibraryParser.class);
            logger.error("Ошибка разбора файла " + file.toString(), e);
            throw new PhotoLibraryParserException("parse error", e);
        }
    }

    private String getRealPhotoPath(
            File libraryFile,
            String libraryPath,
            String photoPathInXml
    ) {
        String rootRealPath = libraryFile.getParent();
        return photoPathInXml.replaceAll(libraryPath, rootRealPath);
    }
}
