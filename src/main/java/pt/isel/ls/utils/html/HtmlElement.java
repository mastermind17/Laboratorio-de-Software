package pt.isel.ls.utils.html;

import java.util.HashMap;
import java.util.Map;

public class HtmlElement {

	private String tag;
	private String content;
	private Map<String,String> attributes;

	public HtmlElement(String tag){
		this(tag, "");
	}

	public HtmlElement(String tag, String content){
		this.tag = tag;
		this.content = content;
		attributes = new HashMap<>();
	}

	public String getTag(){ return tag; }
	public String getContent(){ return content; }
	public Map<String,String> getAttributes(){ return attributes; }

	public HtmlElement withAttribute(String attribute, String value){
		attributes.put(attribute, value);
		return this;
	}

//	@Override
//	public ContentText toString(){
//		ContentText html = "<" + tag;
//		return "";
//	}
}
