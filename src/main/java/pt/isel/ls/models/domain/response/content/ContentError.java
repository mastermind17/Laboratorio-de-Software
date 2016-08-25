package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

public class ContentError implements IContent {

	private ResponseError error;

	public ContentError(ResponseError error){
		this.error = error;
	}

	@Override
	public String toPlainString(){ return error.getMessage(); }

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", "LS PROJECT"))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				.addElement(new HtmlElement("h3", String.format("Error Code: %d %s", error.getStatusCode().valueOf(), error.getStatusCode())))
				.openTag("hr")
				.addElement(new HtmlElement("h3", error.getMessage()))
				.openTag("hr")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.selfClosingTag("br")
				.closeTag("div")
				.closeTag("div")
				.addContent(CommonHtml.getFooter())
				.closeTag("div")
				.closeTag("body")
				.closeTag("html");
		return html.toString();
	}

}
