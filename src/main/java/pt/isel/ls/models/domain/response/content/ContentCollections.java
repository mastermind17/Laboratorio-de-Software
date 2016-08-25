package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

import java.util.List;

public class ContentCollections implements IContent {

	private List<MovieCollection> collections;

	public ContentCollections(List<MovieCollection> collections){
		this.collections = collections;
	}

	@Override
	public String toPlainString() {
		String ls = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append(" --- Collections List ---").append(ls).append(ls);
		for(MovieCollection mc : collections){
			builder.append("Name: ").append(mc.getName()).append(ls);
			builder.append("Description: ").append(mc.getDescription()).append(ls).append(ls);
		}
		return builder.toString();
	}

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", "Collections"))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				.addElement(new HtmlElement("h1", "Collections"))
				.openTag(new HtmlElement("div").withAttribute("align", "center"))
				.openTag("hr")
				.openTag(new HtmlElement("table").withAttribute("id", "hor-minimalist-b"))
				.openTag(new HtmlElement("thead"))
				.openTag(new HtmlElement("tr"))
				.addElement(new HtmlElement("th", "Collection").withAttribute("align", "center"))
				.addElement(new HtmlElement("th", "Description").withAttribute("align", "center"))
				.closeTag("tr")
				.closeTag("thead")
				.openTag("tbody");
		for (MovieCollection col : collections) {
			html	.openTag("tr")
					.openTag(new HtmlElement("td").withAttribute("align", "center"))
					.addElement(new HtmlElement("a", col.getName())
							.withAttribute("href", "/collections/" + col.getId())
							.withAttribute("align", "center"))
					.closeTag("td")
					.addElement(new HtmlElement("td", col.getDescription()).withAttribute("align", "center"))
					.closeTag("tr");
		}
		html	.closeTag("tbody")
				.closeTag("table")
				// next and previous image arrows
				.openTag(new HtmlElement("a").withAttribute("href", "/"))
				.openTag(new HtmlElement("img")
						.withAttribute("src", StaticResources.LEFT_ARROW_URL)
						.withAttribute("alt", "Previous")
						.withAttribute("width", "23")
						.withAttribute("height", "19"))
				.closeTag("a")
				.openTag(new HtmlElement("a").withAttribute("href", "/"))
				.openTag(new HtmlElement("img")
						.withAttribute("src", StaticResources.RIGHT_ARROW_URL)
						.withAttribute("alt", "Previous")
						.withAttribute("width", "23")
						.withAttribute("height", "19"))
				.closeTag("a")
				.closeTag("div")
				.openTag("hr")
				// Form
				.addElement(new HtmlElement("h1", "Add Collection"))
				.openTag(new HtmlElement("form").withAttribute("method", "POST"))
				.addContent("Name:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "name")
						.withAttribute("required", ""))
				.openTag("br")
				.addContent("Description:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "description")
						.withAttribute("required", ""))
				.openTag("br")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "submit")
						.withAttribute("value", "submit"))
				.openTag("hr")
				// Page end
				.closeTag("div")
				.closeTag("div")
				.addContent(CommonHtml.getFooter())
				.closeTag("div")
				.closeTag("body")
				.closeTag("html");
		return html.toString();
	}

}
