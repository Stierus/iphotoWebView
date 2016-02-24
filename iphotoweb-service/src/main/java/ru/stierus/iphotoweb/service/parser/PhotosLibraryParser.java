package ru.stierus.iphotoweb.service.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stierus.iphotoweb.service.model.AlbumInfo;
import ru.stierus.iphotoweb.service.model.PhotoInfo;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.File;

public class PhotosLibraryParser {

    public PhotoLibrary parseAlbumData(File file) throws PhotoLibraryParserException {
        try {
            Logger logger = LoggerFactory.getLogger(PhotosLibraryParser.class);
            logger.info("Начало разбора файла " + file.toString());
            PhotoLibrary.PhotoLibraryBuilder photoLibraryBuilder = PhotoLibrary.builder();

            Connection conn = null;
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:".concat(file.toString()).concat("/database/Library.apdb");
            logger.info("Trying to get connection: " + url);

            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            ResultSet resSet = statement.executeQuery("SELECT t.* FROM RKAlbum t WHERE isHidden=0 AND isInTrash=0 AND name IS NOT NULL");

            while(resSet.next())
            {
                AlbumInfo.AlbumInfoBuilder albumBuilder = AlbumInfo.builder();
                albumBuilder.setId(resSet.getInt("modelId"));
                albumBuilder.setName(resSet.getString("name"));
                albumBuilder.setType(resSet.getString("albumType"));

                Statement subStatement = conn.createStatement();
//                ResultSet photoListQueryResult = subStatement.executeQuery("SELECT t.modelId FROM RKAlbumVersion t WHERE albumId=" + resSet.getInt("modelId"));

                String getImageQuery = "SELECT i.modelId, i.name, i.uuid, i.UTI, i.imagePath " +
                        "FROM RKAlbumVersion a " +
                        "INNER JOIN RKMaster i ON i.modelId = a.modelId " +
                        "WHERE a.albumId=" + resSet.getInt("modelId");

                ResultSet photoListQueryResult = subStatement.executeQuery(getImageQuery);
                Integer qty = 0;
                while(photoListQueryResult.next())
                {
                    Integer photoId = photoListQueryResult.getInt("modelId");
                    albumBuilder.addPhotoId(photoId);
                    qty++;

                    PhotoInfo.PhotoInfoBuilder photoInfoBuilder = PhotoInfo.builder();
                    String realImgPath = file.toString().concat("/Masters/").concat(photoListQueryResult.getString("imagePath"));
                    String thumbImgPath = file.toString().concat("/Thumbnails/").concat(photoListQueryResult.getString("imagePath"));

                    photoInfoBuilder.setCaption(photoListQueryResult.getString("name"));
                    photoInfoBuilder.setGuid(photoListQueryResult.getString("uuid"));
                    photoInfoBuilder.setType(photoListQueryResult.getString("UTI"));
                    photoInfoBuilder.setComment("");
                    photoInfoBuilder.setId(photoId);
                    photoInfoBuilder.setPath(realImgPath);
                    photoInfoBuilder.setThumbPath(thumbImgPath);

                    photoLibraryBuilder.addPhoto(photoInfoBuilder.build());
                }
                subStatement.close();
                photoListQueryResult.close();

                albumBuilder.setPhotoCount(qty);
                photoLibraryBuilder.addAlbum(albumBuilder.build());
            }

            resSet.close();
            statement.close();
            conn.close();

            photoLibraryBuilder.setPath(file.toString());

            logger.info("Файл " + file.toString() + " успешно обработан");
            return photoLibraryBuilder.build();
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(PhotosLibraryParser.class);
            logger.error("Ошибка разбора файла " + file.toString(), e);
            throw new PhotoLibraryParserException("parse error", e);
        }
    }
}
