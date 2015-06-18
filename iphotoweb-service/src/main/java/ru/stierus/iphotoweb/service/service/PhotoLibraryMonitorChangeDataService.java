package ru.stierus.iphotoweb.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pavel on 17.06.15.
 */
public class PhotoLibraryMonitorChangeDataService implements PhotoLibraryMonitorService {

    private List<Path> paths = new ArrayList<>();
    private Long lastCheck = 0l;

    public void addLibrary(Path pathToLibrary){
        paths.add(pathToLibrary);
    }

    public void removeLibrary(Path pathToLibrary){
        paths.remove(pathToLibrary);
    }

    /**
     * @return list of path to changed libraries
     */
    public List<Path> checkLibraries(){
        Logger logger = LoggerFactory.getLogger(PhotoLibraryMonitorChangeDataService.class);
        logger.info("Начало поиска изменившихся библиотек");
        Long deltaLastCheck = new Date().getTime() - lastCheck;
        lastCheck = new Date().getTime();
        List<Path> pathsToReIndex = new ArrayList<>();

        paths.forEach(path -> {
            try{
                Date lastModified = new Date(path.toFile().lastModified());
                Date now = new Date();
                if((now.getTime() - lastModified.getTime()) < deltaLastCheck){
                    pathsToReIndex.add(path);
                }
            }catch (Throwable e){
                logger.error(e.getMessage(), e);
            }
        });
        logger.info("Поиск завершен, найдено библиотек: " + pathsToReIndex.size());
        return pathsToReIndex;
    }
}
