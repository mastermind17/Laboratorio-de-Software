package pt.isel.ls.services.http;

import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.commands.ICommand;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class AppServer extends HttpServlet {

	public AppServer() {}

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Request request = Request.valueOf(req);
		Dispatcher dispatcher = new Dispatcher();
		ICommand cmd = dispatcher.getMatchingCommand(request);
		Response response = cmd.execute(request);
		response.send(res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Request request = Request.valueOf(req);
		Dispatcher dispatcher = new Dispatcher();
		ICommand cmd = dispatcher.getMatchingCommand(request);
		Response response = cmd.execute(request);
		if(response.getHeaders().containsKey("Location"))
			res.sendRedirect(response.getHeaders().get("Location"));
		else
			response.send(res);
    }
}
