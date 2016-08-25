package pt.isel.ls.utils.html;

public class HtmlPage {

	private StringBuilder builder;

	public HtmlPage(){
		builder = new StringBuilder();
	}

	public HtmlPage addDoctype(String doctype){
		builder.append("<!DOCTYPE ").append(doctype).append(">");
		return this;
	}

	public HtmlPage selfClosingTag(String tag){
		builder.append("<").append(tag).append("/>");
		return this;
	}

	public HtmlPage openTag(String tag){
		builder.append("<").append(tag).append(">");
		return this;
	}

	public HtmlPage openTag(HtmlElement element){
		builder.append("<").append(element.getTag());
		element.getAttributes().forEach(
				(key, value) -> builder.append(" ").append(key).append("=\"").append(value).append("\""));
		builder.append(">");
		return this;
	}

	public HtmlPage closeTag(String tag){
		builder.append("</").append(tag).append(">");
		return this;
	}

	public HtmlPage addContent(String content){
		builder.append(content);
		return this;
	}

	public HtmlPage addElement(HtmlElement element){
		openTag(element);
		addContent(element.getContent());
		closeTag(element.getTag());
		return this;
	}

	@Override
	public String toString(){
		return builder.toString();
	}
}
