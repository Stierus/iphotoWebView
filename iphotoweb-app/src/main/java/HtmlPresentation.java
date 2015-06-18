import ru.stierus.iphotoweb.service.model.PhotoLibrary;

/**
 * Created by pavel on 15.06.15.
 */
public class HtmlPresentation  extends Thread
{
    protected PhotoLibrary library;

    @Override
    public void run()
    {
        do {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true);
    }

    public HtmlPresentation(PhotoLibrary library) {
        this.library = library;
    }

    public void updateLibrary(PhotoLibrary library) {
        //@TODO Информируем клиентов об обновлении информации
        this.library = library;
    }
}
