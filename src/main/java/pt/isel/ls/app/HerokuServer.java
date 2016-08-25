package pt.isel.ls.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.services.http.AppServer;

public class HerokuServer {

	public static void main(String[] args) throws Exception {
		App app = new App(new String[]{});
		Server server = new Server(Integer.valueOf(System.getenv("PORT")));
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addServlet(new ServletHolder(new AppServer()),"/*");
		server.start();
		server.join();
	}
}
