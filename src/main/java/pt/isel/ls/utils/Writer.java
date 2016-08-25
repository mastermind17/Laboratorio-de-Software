package pt.isel.ls.utils;

import pt.isel.ls.models.domain.response.Response;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Writer {

	private static final String OUT_DIR = "";

	public static void handleResponse(Response res){
		if(res.getHeaders().containsKey("file-name"))
			writeToFile(res.getHeaders().get("file-name"),
					res.getContent() != null ? res.getContent() : res.getContent());
		else
			writeToConsole(res.getContent() != null ? res.getContent() : res.getContent());
	}

	private static void writeToConsole(String str){
		System.out.println(str);
	}

	private static void writeToFile(String file, String str){
		PrintWriter writer;
		try {
			writer = new PrintWriter(OUT_DIR + file, "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

}
