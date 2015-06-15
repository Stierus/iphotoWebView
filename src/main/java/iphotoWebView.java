
public class iphotoWebView {
    public static void main(String[] args) throws Exception {
        String path = "/Users/pavel/coding/iphoto/data/test.photolibrary/AlbumData.xml";

        InfoParser parser = new InfoParser();
        PhotoLibrary library = parser.parseAlbum(path);

        System.out.println("result: ");
        System.out.println(library.toString());
    }
}
