package ru.stierus.iphoto.web.servlet;

import ru.stierus.iphotoweb.service.model.PhotoInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pavel on 15.07.15.
 */
public class PhotoServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            response.setContentType("image/jpg");
            response.setStatus(HttpServletResponse.SC_OK);


            test(request, response);

        } catch (IllegalArgumentException e) {
            setError(response);
        }
    }

    private String test(HttpServletRequest request, HttpServletResponse response)
    {
        final Path path;
        try {
            path = getFilePath(request);
            if (path == null) {
                throw new Exception("cant resolve file path");
            }



        } catch (Exception e) {
            e.printStackTrace();
            setError(response);
        }


        return "";
    }

    private HttpServletResponse setError(HttpServletResponse response)
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        try {
            response.getWriter().println("<html>\n" +
                    "<head><title>404 Not Found</title></head>\n" +
                    "<body bgcolor=\"white\">\n" +
                    "<center><h1>404 Not Found</h1></center>\n" +
                    "<hr><center>nginx/1.6.2</center>\n" +
                    "</body>\n" +
                    "</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private Path getFilePath(HttpServletRequest request) throws Exception {
        // Decode the path.
        String uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");

        if (uri.isEmpty() || uri.charAt(0) != '/') {
            return null;
        }
        // Convert file separators.
        uri = uri.replace('/', File.separatorChar);

        Path uriPath = Paths.get(uri);

        String imgHash = uriPath.getName(uriPath.getNameCount() - 1).toString();

        String photoSize = uriPath.getName(0).toString();

        imgHash = imgHash.replaceAll("\\.[a-zA-Z]+$", "");

        PhotoInfo photo = getLibrary().getPhotoByGuid(imgHash);

        if (photoSize.toLowerCase() == "thumb") {
            return Paths.get(photo.getThumbPath());
        } else {
            return Paths.get(photo.getPath());
        }
        //http://localhost:9292/photo/full/asggstrh.jpg
    }
}
