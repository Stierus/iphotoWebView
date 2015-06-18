package ru.stierus.iphoto;

import ru.stierus.iphoto.web.controller.TestListener;
import ru.stierus.iphotoweb.service.parser.IPhotoLibraryParser;
import ru.stierus.iphotoweb.service.service.PhotoLibraryMonitorChangeDataService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryParserService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryService;

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



        System.out.println("result: ");
//        System.out.println(library.toString());
    }
}
