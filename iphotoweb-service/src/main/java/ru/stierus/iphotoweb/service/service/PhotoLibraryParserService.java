package ru.stierus.iphotoweb.service.service;

import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.parser.IPhotoLibraryParser;
import ru.stierus.iphotoweb.service.parser.IPhotoLibraryParserException;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PhotoLibraryParserService {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final PhotoLibraryService photoLibraryService;
    private final IPhotoLibraryParser parser;
    private final PhotoLibraryMonitorService monitorService;

    public PhotoLibraryParserService(PhotoLibraryService photoLibraryService, IPhotoLibraryParser parser, PhotoLibraryMonitorService monitorService) {
        this.photoLibraryService = photoLibraryService;
        this.parser = parser;
        this.monitorService = monitorService;
    }

    public void start() {
        executorService.schedule(this::monitor, 5, TimeUnit.SECONDS);
    }

    private void monitor(){
        List<Path> paths = monitorService.checkLibraries();
        for (Path path : paths) {
            try {
                PhotoLibrary photoLibrary = parser.parseAlbumData(path.toFile());
                photoLibraryService.updateLibrary(photoLibrary);
            } catch (IPhotoLibraryParserException e) {
                // @todo log
            }
        }

    }
}
