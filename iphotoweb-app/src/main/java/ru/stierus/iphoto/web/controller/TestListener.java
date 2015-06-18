package ru.stierus.iphoto.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.service.PhotoLibraryListener;

/**
 * Created by pavel on 18.06.15.
 */
public class TestListener implements PhotoLibraryListener {

    @Override
    public void onChange(PhotoLibrary library) {
        Logger logger = LoggerFactory.getLogger(TestListener.class);
        logger.info("Library changed: ");
        logger.info(library.toString());
    }
}
