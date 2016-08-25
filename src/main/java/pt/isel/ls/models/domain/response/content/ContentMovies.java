package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

import java.util.List;

public class ContentMovies implements IContent {

	private List<Movie> movies;

	public ContentMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@SuppressWarnings("Duplicates")
	@Override
	public String toPlainString() {
		String ls = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("List of Movies ").append(ls).append(ls);
		for (Movie movie : movies) {
			builder.append(" --- Movie ---").append(ls);
			builder.append("title: ").append(movie.getTitle()).append(ls);
			builder.append("release year:").append(movie.getReleaseYear()).append(ls).append(ls);
		}
		return builder.toString();
	}

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", "Movies"))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				.addElement(new HtmlElement("h1", "Movies"))
				.openTag(new HtmlElement("div").withAttribute("align", "center"))
				.openTag("hr")
				.openTag(new HtmlElement("table").withAttribute("id", "hor-minimalist-b"))
				.openTag(new HtmlElement("thead"))
				.openTag(new HtmlElement("tr"))
				.openTag(new HtmlElement("th").withAttribute("align", "center"))
				.addElement(new HtmlElement("button", "#")
						.withAttribute("type", "button")
						.withAttribute("id", "addedDate")
						.withAttribute("align", "center")
						.withAttribute("onclick", "sortTable('addedDate')"))
				.closeTag("th")
				.openTag(new HtmlElement("th").withAttribute("align", "center"))
				.addElement(new HtmlElement("button", "Title")
						.withAttribute("type", "button")
						.withAttribute("id", "title")
						.withAttribute("align", "center")
						.withAttribute("onclick", "sortTable('title')"))
				.closeTag("th")
				.openTag(new HtmlElement("th").withAttribute("align", "center"))
				.addElement(new HtmlElement("button", "Year")
						.withAttribute("type", "button")
						.withAttribute("id", "year")
						.withAttribute("align", "center")
						.withAttribute("onclick", "sortTable('year')"))
				.closeTag("th")
				.openTag(new HtmlElement("th").withAttribute("align", "center"))
				.addElement(new HtmlElement("button", "Rating")
						.withAttribute("type", "button")
						.withAttribute("id", "rating")
						.withAttribute("align", "center")
						.withAttribute("onclick", "sortTable('rating')"))
				.closeTag("th")
				.closeTag("tr")
				.closeTag("thead")
				.openTag("tbody");
		for (Movie mov : movies) {
			html	.openTag("tr")
					.addElement(new HtmlElement("td", String.valueOf(mov.getId())).withAttribute("align", "center"))
					.openTag(new HtmlElement("td", mov.getTitle()).withAttribute("align", "center"))
					.addElement(new HtmlElement("a", mov.getTitle())
							.withAttribute("href", "/movies/" + mov.getId())
							.withAttribute("align", "center"))
					.closeTag("td")
					.addElement(new HtmlElement("td", String.valueOf(mov.getReleaseYear())).withAttribute("align", "center"))
					.addElement(new HtmlElement("td", mov.getAverageRatingRepresentation()).withAttribute("align", "center"))
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
				.addElement(new HtmlElement("h3", "Add Movie"))
				.openTag(new HtmlElement("form").withAttribute("method", "POST"))
				.addContent("Title:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "title")
						.withAttribute("required", ""))
				.openTag("br")
				.addContent("Release Year:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "number")
						.withAttribute("name", "releaseYear")
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
				.addContent(StaticResources.getSortMoviesTableScript())
				.closeTag("body")
				.closeTag("html");
		return html.toString();
	}
}