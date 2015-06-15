import com.dd.plist.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by pavel on 05.06.15.
 */
public class InfoParser {

    public PhotoLibrary parseAlbum(String path) throws Exception {
        try {
            File file = new File(path);
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
            NSObject[] albumList = ((NSArray)rootDict.objectForKey("List of Albums")).getArray();

            HashMap<String, AlbumInfo> albums = new HashMap<String, AlbumInfo>();
            HashMap<Integer, PhotoInfo> photos = new HashMap<Integer, PhotoInfo>();

            for (int i = 0; i < albumList.length; i++) {
                NSDictionary album = (NSDictionary) albumList[i];
                AlbumInfo albumInf = new AlbumInfo();
                albumInf.setId(Integer.parseInt(album.objectForKey("AlbumId").toString()));
                albumInf.setName(album.objectForKey("AlbumName").toString());
                albumInf.setPhotoCount(Integer.parseInt(album.objectForKey("PhotoCount").toString()));
                albumInf.setType(album.objectForKey("Album Type").toString());
                NSObject[] keyList = ((NSArray)album.objectForKey("KeyList")).getArray();
                for (int j = 0; j < keyList.length; j++) {
                    Integer photoId = Integer.parseInt(keyList[j].toString());
                    albumInf.addPhotoId(photoId);
                }
                albums.put(albumInf.getName(), albumInf);
            }

            HashMap<String, NSObject> photoList = ((NSDictionary)rootDict.objectForKey("Master Image List")).getHashMap();
            Iterator photoIterator = photoList.entrySet().iterator();
            while (photoIterator.hasNext()) {
                Map.Entry photoSet = (Map.Entry) photoIterator.next();

                NSDictionary image = (NSDictionary) photoSet.getValue();
                PhotoInfo photoInf = new PhotoInfo();
                photoInf.setCaption(image.objectForKey("Caption").toString());
                photoInf.setGuid(image.objectForKey("GUID").toString());
                photoInf.setPath(image.objectForKey("ImagePath").toString());
                photoInf.setThumbPath(image.objectForKey("ThumbPath").toString());
                photoInf.setType(image.objectForKey("MediaType").toString());
                photos.put(Integer.parseInt(photoSet.getKey().toString()), photoInf);
            }
            PhotoLibrary library = new PhotoLibrary(albums, photos);
            return library;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new Exception("execution failed");
    }
}
