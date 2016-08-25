package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

public class ContentCollection implements IContent {

	private MovieCollection movieCollection;

	public ContentCollection(MovieCollection mc){
		movieCollection = mc;
	}

	@Override
	public String toPlainString() {
		String ls = System.getProperty("line.separator");
		String ms = "";
		for(Movie mov : movieCollection.getMovies()) {
			ms += " " + mov.getTitle() + ";";
		}
		return
				" --- MovieCollection ---" + ls +
				"Name: " + movieCollection.getName() + ls +
				"Description: " + movieCollection.getDescription() + ls +
				"Movies: " + ms;
	}

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", movieCollection.getName()))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				.addElement(new HtmlElement("h1", movieCollection.getName()))
				.openTag("hr")
				// collection info section
				.addElement(new HtmlElement("h3", "Collection Info"))
				.openTag("ul")
				.openTag("li")
				.addElement(new HtmlElement("b", "Description: "))
				.addContent(String.valueOf(movieCollection.getDescription()))
				.closeTag("li")
				.closeTag("ul")
				.openTag("hr")
				.addElement(new HtmlElement("h3", "Movies"))
				.openTag("ul");
		// movies section
		for (Movie movie : movieCollection.getMovies()) {
			html
					.openTag("li")
					.addElement(new HtmlElement("a", movie.getTitle() + " (" + movie.getReleaseYear() + ")")
							.withAttribute("href", "/movies/" + movie.getId())
							.withAttribute("align", "center"))
					.closeTag("li");
		}
		if(movieCollection.getMovies().length == 0)
			html.addElement(new HtmlElement("li", "No movies in here"));
		html	.closeTag("ul")
				.openTag("hr")
				// Form
				.addElement(new HtmlElement("h1", "Add Movie"))
				.openTag(new HtmlElement("form")
						.withAttribute("method", "POST")
						.withAttribute("action", "/collections/" + movieCollection.getId() + "/movies/"))
				.addContent("Id:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "mid")
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
