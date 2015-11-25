package ru.stierus.iphotoweb.service.parser;

import ru.stierus.iphotoweb.service.model.PhotoLibrary;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by pavel on 31.08.15.
 */
public class IPhotoLibraryParserTest {

    @org.junit.Test
    public void testParseAlbumData() throws Exception {

        IPhotoLibraryParser parser = new IPhotoLibraryParser();

        Path path = Paths.get("/Users/pavel/coding/java/data/test.photolibrary/AlbumData.xml");

        PhotoLibrary library = parser.parseAlbumData(path.toFile());

        String test = ";";
    }
}