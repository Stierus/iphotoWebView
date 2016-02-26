package ru.stierus.iphoto.web.servlet;

import com.sun.xml.internal.ws.util.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stierus.iphotoweb.service.model.PhotoInfo;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pavel on 15.07.15.
 */
public class PhotoServlet extends BaseServlet {

    private static final String ORIGINAL_CODE = "original";
    private static final String THUMBNAIL_CODE = "thumbnail";

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

    private HttpServletResponse test(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            HashMap<String, String> info = getUriInfo(request);

            PhotoInfo photo = getLibrary().getPhotoByGuid(info.get("imgHash"));
            String path = "";

            if (info.get("photoSize") == THUMBNAIL_CODE) {
                path = photo.getThumbPath();
            } else {
                path = photo.getPath();
            }

            File f = new File(path);
            byte[] arBytes = new byte[(int)f.length()];
            FileInputStream is = new FileInputStream(f);
            is.read(arBytes);
            ServletOutputStream op = response.getOutputStream();
            op.write(arBytes);
            op.flush();
            is.close();
        } catch (Exception e) {
            setError(response);
        }

        return response;
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

    private Path getFilePath(HttpServletRequest request) throws Exception
    {
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

    private HashMap<String, String> getUriInfo(HttpServletRequest request) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();

        try {
            // Decode the path.
            String uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");

            if (uri.isEmpty() || uri.charAt(0) != '/') {
                throw new Exception("Empty uri");
            }
            // Convert file separators.
            uri = uri.replace('/', File.separatorChar);

            Path uriPath = Paths.get(uri);

            String imgHash = uriPath.getName(uriPath.getNameCount() - 1).toString();
            imgHash = imgHash.replaceAll("\\.[a-zA-Z]+$", "");

            String photoSize = uriPath.getName(1).toString();
            List<String> sizeList = getSizes();
            if (!sizeList.contains(photoSize)) {
                throw new Exception("Unknown image size");
            }

            params.put("imgHash", imgHash);
            params.put("photoSize", photoSize);

        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(PhotoServlet.class);
            logger.info("Cant decode user request", e);
            throw new Exception("Invalid request params");
        }


        return params;
    }


    private List<String> getSizes()
    {
        List<String> sizes = new ArrayList<String>();
        sizes.add(ORIGINAL_CODE);
        sizes.add(THUMBNAIL_CODE);
//        sizes.add("preview");
        return sizes;
    }
}
