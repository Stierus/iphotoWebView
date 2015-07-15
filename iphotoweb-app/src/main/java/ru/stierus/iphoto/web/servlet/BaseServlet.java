package ru.stierus.iphoto.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.service.PhotoLibraryListener;

import javax.servlet.http.HttpServlet;

/**
 * Created by pavel on 13.07.15.
 */
public class BaseServlet extends HttpServlet implements PhotoLibraryListener{

    private PhotoLibrary library;

    @Override
    public void onChange(PhotoLibrary library) {
        this.library = library;
        Logger logger = LoggerFactory.getLogger("BaseServlet");
        logger.info("Library changed (BaseServlet): ");
        logger.info(library.toString());
    }

    public PhotoLibrary getLibrary() {
        return library;
    }
}
