import com.dd.plist.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * Created by pavel on 05.06.15.
 */
public class InfoParser {

    public photoLibrary parseAlbum(String path) throws Exception {
        try {
            File file = new File(path);
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
            NSObject[] albumList = ((NSArray)rootDict.objectForKey("List of Albums")).getArray();

            HashMap<String, AlbumInfo> albums = new HashMap<String, AlbumInfo>();
            HashMap<Integer, photoInfo> photos = new HashMap<Integer, photoInfo>();

            for (int i = 0; i < albumList.length; i++) {
                NSObject album = albumList[i];
                AlbumInfo albumInf = new AlbumInfo();
                albumInf->setId(10);
            }
//            NSObject rootObject = plists[0];

            System.out.println(albumList);

            photoLibrary library = new photoLibrary(albums, photos);
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
