package pt.isel.ls.app;

import pt.isel.ls.models.domain.commands.ICommand;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.services.sql.ConnectionWrapper;
import pt.isel.ls.utils.Writer;

import java.util.Scanner;

public class App {

	private String[] programArgs;
	private static ConnectionWrapper connectionWrapper;

	public App(String[] args) {
		programArgs = args;
		connectionWrapper = new ConnectionWrapper();
	}

	void run() throws Exception {
		if(programArgs.length == 0)
			interactiveMode();
		else
			handleOneCommand(programArgs);
	}

	private Request parseArgs(){
		Scanner in = new Scanner(System.in);
		while(true){
			String[] args = in.nextLine().split(" ");
			try {
				return Request.valueOf(args);
			}catch (Exception ex){
				System.out.println("Malformed arguments.");
			}
		}
	}

	/**
	 * The program keeps reading and executing commands from the stdin
	 * until an EXIT method is given.
	 */
	private void interactiveMode(){
		Dispatcher dis = new Dispatcher();
		while(true){
			Request req = parseArgs();
			ICommand cmd = dis.getMatchingCommand(req);
			Response res = cmd.execute(req);
			Writer.handleResponse(res);
		}
	}

	/**
	 * Read and execute only one command.
	 */
	private void handleOneCommand(String[] args) throws Exception {
		Dispatcher dis = new Dispatcher();
		Request req;
		try {
			req = Request.valueOf(args);
		}catch (Exception ex){
			System.out.println("Malformed arguments.");
			return;
		}
		ICommand cmd = dis.getMatchingCommand(req);
		Response res = cmd.execute(req);
		Writer.handleResponse(res);
	}

	public static ConnectionWrapper getConnectionWrapper() { return connectionWrapper; }
}