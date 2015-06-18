package ru.stierus.iphoto;

import ru.stierus.iphoto.web.controller.TestListener;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.parser.IPhotoLibraryParser;
import ru.stierus.iphotoweb.service.service.PhotoLibraryMonitorChangeDataService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryParserService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryService;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "/Users/pavel/coding/iphoto/data/test.photolibrary/AlbumData.xml";
        PhotoLibraryService photoLibraryService = new PhotoLibraryService();
        photoLibraryService.addListener(new TestListener());

        PhotoLibraryMonitorChangeDataService monitorService = new PhotoLibraryMonitorChangeDataService();
        monitorService.addLibrary(Paths.get(path));

        PhotoLibraryParserService parseService = new PhotoLibraryParserService(
                photoLibraryService,
                new IPhotoLibraryParser(),
                monitorService
        );

        parseService.start();

//        IPhotoLibraryParser parser = new IPhotoLibraryParser();
//
//        PhotoLibrary library = parser.parseAlbumData(Paths.get(path).toFile());
//
//        System.out.println("result: ");
//        System.out.println(library.toString());
    }
}
