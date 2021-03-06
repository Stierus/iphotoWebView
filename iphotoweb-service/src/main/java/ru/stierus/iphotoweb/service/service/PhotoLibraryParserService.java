package ru.stierus.iphotoweb.service.service;

import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.parser.LibraryParser;
import ru.stierus.iphotoweb.service.parser.PhotoLibraryParserException;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoLibraryParserService {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final PhotoLibraryService photoLibraryService;
    private final LibraryParser parser;
    private final PhotoLibraryMonitorService monitorService;

    public PhotoLibraryParserService(PhotoLibraryService photoLibraryService, LibraryParser parser, PhotoLibraryMonitorService monitorService) {
        this.photoLibraryService = photoLibraryService;
        this.parser = parser;
        this.monitorService = monitorService;
    }

    public void start() {
        executorService.scheduleWithFixedDelay(this::monitor, 0, 5, TimeUnit.SECONDS);
    }

    private void monitor(){
        Logger logger = LoggerFactory.getLogger(LibraryParser.class);

        List<Path> paths = monitorService.checkLibraries();
        /** @TODO в случае невозможности распарсить медиатеку - повторять попытку позже, а не игнорировать изменения **/
        for (Path path : paths) {
            try {
                PhotoLibrary photoLibrary = parser.parseAlbumData(path.toFile());
                photoLibraryService.updateLibrary(photoLibrary);
            } catch (PhotoLibraryParserException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
