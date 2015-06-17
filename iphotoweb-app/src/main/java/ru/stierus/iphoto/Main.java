package ru.stierus.iphoto;

import ru.stierus.iphoto.parser.controller.IPhotoAlbumDataParser;
import ru.stierus.iphoto.parser.model.PhotoLibrary;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "/Users/pavel/coding/iphoto/data/test.photolibrary/AlbumData.xml";

        ru.stierus.iphoto.parser.controller.IPhotoAlbumDataParser parser = new ru.stierus.iphoto.parser.controller.IPhotoAlbumDataParser();
        PhotoLibrary library = parser.parseAlbum(path);


        System.out.println("result: ");
        System.out.println(library.toString());
    }
}
