package pt.isel.ls.models.domain.response;

import pt.isel.ls.models.domain.response.content.IContent;
import pt.isel.ls.services.http.HttpStatusCode;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Response {

	private IContent content;
    private final HttpStatusCode status = HttpStatusCode.Ok;
	private final HttpStatusCode statusCode;
	private final Charset charset = Charset.forName("UTF-8");

	private HashMap<String,String> headers;

	public Response(HttpStatusCode statusCode, IContent content, HashMap<String,String> headers){
		this.statusCode = statusCode;
		this.content = content;
		this.headers = headers != null ? headers : getDefaultHeaders();
	}

	public HashMap<String,String> getHeaders() { return headers; }

	public String getContent(){
		switch (headers.get("Content-Type")){
			case "text/plain":	return content.toPlainString();
			case "text/html":	return content.toHtmlString();
			default:			return content.toPlainString();
		}
	}

	// TODO acho que já não é usado
	private HashMap<String,String> getDefaultHeaders(){
		HashMap<String,String> out = new HashMap<>();
		out.put("Content-Type", "text/html");
		return out;
	}

    public void send(HttpServletResponse resp) throws IOException {
        for(Map.Entry<String, String> entry : headers.entrySet()){
            resp.addHeader(entry.getKey(), entry.getValue());
        }
//        if(_body == null) {
//            sendWithoutBody(resp);
//        }else {
            sendWithBufferedBody(resp);
//        }
    }

    private void sendWithoutBody(HttpServletResponse resp) throws IOException {
        resp.setStatus(statusCode.valueOf());
    }

    private void sendWithBufferedBody(HttpServletResponse resp) throws IOException {

        ByteArrayOutputStream _os = new ByteArrayOutputStream();
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(_os, charset))){
//            _body.writeTo(writer);
            writer.write(getContent());
        }
        byte[] bytes = _os.toByteArray();
        resp.setStatus(statusCode.valueOf());
		//String ctype = String.format("%s;charset=%s",_body.getMediaType(), charset.name());
        String ctype = String.format("%s;charset=%s",headers.get("Content-Type"), charset.name());
        resp.setContentType(ctype);
        resp.setContentLength(bytes.length);
        OutputStream ros = resp.getOutputStream();
        ros.write(bytes);
        ros.close();
    }
}
