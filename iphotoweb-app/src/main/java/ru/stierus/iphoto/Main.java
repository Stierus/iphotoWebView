package ru.stierus.iphoto;

import ru.stierus.iphoto.web.controller.MainModule;
import ru.stierus.iphoto.web.controller.TestListener;
import ru.stierus.iphotoweb.service.parser.LibraryParser;
import ru.stierus.iphotoweb.service.service.PhotoLibraryMonitorChangeDataService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryParserService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryService;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        PhotoLibraryService photoLibraryService = new PhotoLibraryService();
        MainModule view = new MainModule();
        photoLibraryService.addListener(new TestListener());
        photoLibraryService.addListener(view);

        PhotoLibraryMonitorChangeDataService monitorService = new PhotoLibraryMonitorChangeDataService();
//        monitorService.addLibrary(Paths.get("/Users/pavel.kuznetsov/coding/java/data/test.photolibrary"));
        monitorService.addLibrary(Paths.get("/Users/pavel.kuznetsov/coding/java/data/Photo_test.photoslibrary"));

        PhotoLibraryParserService parseService = new PhotoLibraryParserService(
                photoLibraryService,
                new LibraryParser(),
                monitorService
        );

        parseService.start();
        view.initialize();
    }
}
