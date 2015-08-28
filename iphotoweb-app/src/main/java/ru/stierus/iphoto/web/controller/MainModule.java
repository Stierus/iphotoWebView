package ru.stierus.iphoto.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stierus.iphoto.web.servlet.*;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.service.PhotoLibraryListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.util.*;

/**
 * Created by pavel on 16.06.15.
 */
public class MainModule implements PhotoLibraryListener
    {
        protected ArrayList<BaseServlet> servletList = new ArrayList<BaseServlet> ();

        public void initialize(){
            Logger logger = LoggerFactory.getLogger(MainModule.class);
            try{
                Server server = new Server(9292);
                ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                context.setContextPath("/");
                server.setHandler(context);

                IndexServlet indexServlet = new IndexServlet();
                context.addServlet(new ServletHolder(indexServlet),"/");
                servletList.add(indexServlet);

                PhotoListServlet photoListServlet = new PhotoListServlet();
                context.addServlet(new ServletHolder(photoListServlet),"/photos");
                servletList.add(photoListServlet);

                AlbumListServlet albumListServlet = new AlbumListServlet();
                context.addServlet(new ServletHolder(albumListServlet),"/albums");
                servletList.add(albumListServlet);

//                StaticFilesServlet staticFilesServlet = new StaticFilesServlet();
//                context.addServlet(new ServletHolder(staticFilesServlet),"/js/*");
//                context.addServlet(new ServletHolder(staticFilesServlet),"/css/*");

                server.start();
                server.join();
            }
            catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }

        @Override
        public void onChange(PhotoLibrary library) {
            for(BaseServlet servlet : servletList) {
                servlet.onChange(library);
            }
        }
    }