package ru.stierus.iphoto.web.servlet;

import com.google.common.collect.ImmutableList;
import com.oracle.javafx.jmx.json.JSONDocument;
import ru.stierus.iphotoweb.service.model.AlbumInfo;
import ru.stierus.iphotoweb.service.model.PhotoLibrary;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PhotoListServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            if (request.getParameter("album") == null) {
                throw new IllegalArgumentException("param 'album' cant be empty");
            }
            if (request.getParameter("offset") == null) {
                throw new IllegalArgumentException("param 'offset' cant be empty");
            }
            if (request.getParameter("limit") == null) {
                throw new IllegalArgumentException("param 'limit' cant be empty");
            }

            JSONDocument imgList = this.getImgList(
                    this.getLibrary(),
                    request.getParameter("album"),
                    Integer.parseInt(request.getParameter("offset")),
                    Integer.parseInt(request.getParameter("limit"))
            );

            response.getWriter().println(successResponse(imgList));
        } catch (IllegalArgumentException e) {
            response.getWriter().println(failedResponse(e));
        }
    }

    private JSONDocument successResponse(JSONDocument imgList)
    {
        JSONDocument response = JSONDocument.createObject();
        response.setBoolean("success", true);
        response.set("data", imgList);

        return response;
    }

    private JSONDocument failedResponse(Exception error)
    {
        JSONDocument response = JSONDocument.createObject();
        response.setBoolean("success", false);
        response.setString("errorMessage", error.getMessage());
        return response;
    }

    private JSONDocument getImgList(
            PhotoLibrary library,
            String albumName,
            Integer offset,
            Integer quantity)
    {
        AlbumInfo album = library.getAlbums().get(albumName);
        Integer photoCnt = album.getPhotoIdList().size();

        if (offset > photoCnt) {
            offset = photoCnt;
        }
        if ((offset + quantity) > photoCnt) {
            quantity = photoCnt - offset;
        }

        JSONDocument imgList = JSONDocument.createArray(quantity);

        if(quantity > 0) {
            ImmutableList<Integer> photoSlice = album.getPhotoIdList().subList(offset, (offset + quantity));
            final Integer[] cnt = {0};

            photoSlice.forEach(imgId -> {
                JSONDocument imgElement = JSONDocument.createObject();
                ru.stierus.iphotoweb.service.model.PhotoInfo imgInfo = library.getPhotos().get(imgId);
                imgElement.setString("guid", imgInfo.getGuid());
                imgElement.setString("caption", imgInfo.getCaption());
                imgElement.setString("comment", imgInfo.getComment());
                imgElement.setString("type", imgInfo.getType());
                imgList.set(cnt[0], imgElement);
                cnt[0]++;
            });
        }

        return imgList;
    }
}
