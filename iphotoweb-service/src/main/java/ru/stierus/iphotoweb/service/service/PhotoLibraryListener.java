package ru.stierus.iphotoweb.service.service;

import ru.stierus.iphotoweb.service.model.PhotoLibrary;

@FunctionalInterface
public interface PhotoLibraryListener {
    void onChange(PhotoLibrary library);
}
