package pt.isel.ls.models.domain.commands.implementations;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.models.domain.commands.AbstractBaseCommand;
import pt.isel.ls.models.domain.commands.components.MethodTemplate;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentText;
import pt.isel.ls.services.http.AppServer;
import pt.isel.ls.services.http.HttpStatusCode;

import java.util.Collections;


public class Listen extends AbstractBaseCommand {

    public Listen(){
        methodTemplate = new MethodTemplate("LISTEN");
        pathTemplate = new PathTemplate("/");
		ParameterRestrictions p1 = new ParameterRestrictions("port", false, String.class, null);
		parametersTemplate = new ParametersTemplate(Collections.singletonList(p1));
	}

	@Override
	public boolean validateHeaders(Request req){
		return true;
	}

	@Override
    public Response execute(Request req) {
		int port = req.getParameters().containsKey("port") ?
				Integer.valueOf(req.getParameters().get("top")) :
				Integer.valueOf(System.getenv("PORT"));
		Server server = new Server(port);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addServlet(new ServletHolder(new AppServer()),"/*");
		try {
			server.start();
			System.out.println("\"Listening at port..." + port);
			//server.join();
			System.in.read();
			server.stop();
		} catch (Exception ex) {
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected servlet error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		return new Response(HttpStatusCode.Ok,
				new ContentText("HTTP Server has stopped."), buildResponseHeaders(req.getHeaders()));
    }

    @Override
    public String toString(){
        return "-> LISTEN / - Start HTTP AppServer.";
    }
}
