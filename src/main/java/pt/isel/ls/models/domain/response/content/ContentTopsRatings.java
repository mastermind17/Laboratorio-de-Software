package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

public class ContentTopsRatings implements IContent {

	public ContentTopsRatings(){}

	@Override
	public String toPlainString() { return null; }

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", "Tops"))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				.addElement(new HtmlElement("h1", "Movies Tops"))
				.openTag("hr")
				// ratings tops section
				.addElement(new HtmlElement("h3", "Ratings"))
				.openTag("ul")
				.openTag("li")
				.addElement(new HtmlElement("a", "Movies with better rating")
						.withAttribute("href", "/tops/5/ratings/higher/average"))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("a", "Movies with worst rating")
						.withAttribute("href", "/tops/5/ratings/lower/average"))
				.closeTag("li")
				.closeTag("ul")
				.openTag("hr")
				// reviews tops section
				.addElement(new HtmlElement("h3", "Reviews"))
				.openTag("ul")
				.openTag("li")
				.addElement(new HtmlElement("a", "Movies with more reviews")
						.withAttribute("href", "/tops/5/reviews/higher/count"))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("a", "Movies with less reviews")
						.withAttribute("href", "/tops/5/reviews/lower/count"))
				.closeTag("li")
				.closeTag("ul")
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
