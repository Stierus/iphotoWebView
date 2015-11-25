package ru.stierus.iphoto.web.servlet;

import com.google.common.collect.ImmutableMap;
import com.oracle.javafx.jmx.json.JSONDocument;
import ru.stierus.iphotoweb.service.model.AlbumInfo;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


public class AlbumListServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            JSONDocument albumList = this.getAlbumList(this.getLibrary());

            response.getWriter().println(successResponse(albumList));
        } catch (IllegalArgumentException e) {
            response.getWriter().println(failedResponse(e));
        }
    }

    private JSONDocument successResponse(JSONDocument albumList)
    {
        JSONDocument response = JSONDocument.createObject();
        response.setBoolean("success", true);
        response.set("data", albumList);

        return response;
    }

    private JSONDocument failedResponse(Exception error)
    {
        JSONDocument response = JSONDocument.createObject();
        response.setBoolean("success", false);
        response.setString("errorMessage", error.getMessage());
        return response;
    }

    private JSONDocument getAlbumList(PhotoLibrary library)
    {
        ImmutableMap<String, AlbumInfo> albums = library.getAlbums();
        Iterator albumIterator = albums.entrySet().iterator();

        JSONDocument albumList = JSONDocument.createArray(albums.size());

        int k = 0;
        while (albumIterator.hasNext()) {
            Map.Entry albumInfo = (Map.Entry) albumIterator.next();
            AlbumInfo album = (AlbumInfo) albumInfo.getValue();

            JSONDocument albumElement = JSONDocument.createObject();
            albumElement.setString("id", album.getId().toString());
            albumElement.setString("name", album.getName());
            albumElement.setString("type", album.getType());
            albumElement.setNumber("photo_quantity", album.getPhotoCount());
            albumList.set(k, albumElement);
            k++;
        }

        return albumList;
    }
}
