package ru.stierus.iphotoweb.service.service;

import java.nio.file.Path;
import java.util.List;

public interface PhotoLibraryMonitorService {

    void addLibrary(Path pathToLibrary);

    void removeLibrary(Path pathToLibrary);

    /**
     * @return list of path to changed libraries
     */
    List<Path> checkLibraries();
}
