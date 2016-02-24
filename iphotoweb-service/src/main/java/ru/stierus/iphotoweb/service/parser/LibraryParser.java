package ru.stierus.iphotoweb.service.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;

import java.io.File;
import java.nio.file.Paths;

public class LibraryParser {

    public PhotoLibrary parseAlbumData(File file) throws PhotoLibraryParserException {
        try {
            Logger logger = LoggerFactory.getLogger(LibraryParser.class);
            logger.info("Начало разбора медиатеки " + file.toString());

            File iphotoXmlAlbumInfoFile = Paths.get(file.toString() + "/AlbumData.xml").toFile();

            if (iphotoXmlAlbumInfoFile.exists()) {
                IPhotoLibraryParser parser = new IPhotoLibraryParser();
                return parser.parseAlbumData(iphotoXmlAlbumInfoFile);
            } else {
                PhotosLibraryParser parser = new PhotosLibraryParser();
                return parser.parseAlbumData(file);
            }
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(LibraryParser.class);
            logger.error("Ошибка разбора файла " + file.toString(), e);
            throw new PhotoLibraryParserException("parse error", e);
        }
    }
}
