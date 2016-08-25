package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

public class ContentAbout implements IContent {

	public ContentAbout(){}

	@Override
	public String toPlainString() { return null; }

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", "ContentAbout"))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				.addElement(new HtmlElement("h1", "15/16 Semester - Class LI41D"))
				.openTag("hr")
				// group members section
				.addElement(new HtmlElement("h3", "Group 12 Members"))
				.openTag("ul")
				.openTag("li")
				.addElement(new HtmlElement("b", "38245: "))
				.addContent("Henrique Calhó")
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("b", "38xxx: "))
				.addContent("Filipe Silvestre")
				.closeTag("li")
				.closeTag("ul")
				.openTag("hr")
				// project info section
				.addElement(new HtmlElement("h3", "Info"))
				.openTag("ul")
				.openTag("li")
				.addElement(new HtmlElement("a", "Github")
						.withAttribute("href", "https://github.com/isel-leic-ls/1516-2-LI41D-G12")
						.withAttribute("target", "_blank"))
				.closeTag("li");
		html	.closeTag("ul")
				.openTag("hr")
				.closeTag("div")
				.closeTag("div")
				.addContent(CommonHtml.getFooter())
				.closeTag("div")
				.closeTag("body")
				.closeTag("html");
		return html.toString();
	}
}
