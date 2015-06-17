import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.stierus.iphoto.parser.model.PhotoLibrary;

/**
 * Created by pavel on 15.06.15.
 */
public class HtmlPresentation  extends Thread
{
    protected PhotoLibrary library;

    @Override
    public void run()
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(AsyncEchoServlet.class, "/echo/*");
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
        server.setHandler(handlers);
        server.start();
        server.join();



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
