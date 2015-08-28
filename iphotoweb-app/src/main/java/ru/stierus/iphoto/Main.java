package ru.stierus.iphoto;

import ru.stierus.iphoto.web.controller.MainModule;
import ru.stierus.iphoto.web.controller.TestListener;
import ru.stierus.iphotoweb.service.parser.IPhotoLibraryParser;
import ru.stierus.iphotoweb.service.service.PhotoLibraryMonitorChangeDataService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryParserService;
import ru.stierus.iphotoweb.service.service.PhotoLibraryService;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "/Users/pavel/coding/java/data/test.photolibrary/AlbumData.xml";
        PhotoLibraryService photoLibraryService = new PhotoLibraryService();
        MainModule view = new MainModule();
        photoLibraryService.addListener(new TestListener());
        photoLibraryService.addListener(view);

        PhotoLibraryMonitorChangeDataService monitorService = new PhotoLibraryMonitorChangeDataService();
        monitorService.addLibrary(Paths.get(path));

        PhotoLibraryParserService parseService = new PhotoLibraryParserService(
                photoLibraryService,
                new IPhotoLibraryParser(),
                monitorService
        );

        parseService.start();
        view.initialize();
    }
}
