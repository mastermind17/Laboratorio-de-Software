package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

public class ContentMovieRatings implements IContent {

	private Movie movie;

	public ContentMovieRatings(Movie movie){
		this.movie = movie;
	}

	@Override
	public String toPlainString() {
		String newLine = System.getProperty("line.separator");
		StringBuilder str = new StringBuilder();
		str.append("Movie: ").append(movie.getTitle()).append(newLine);
		str.append("ratings: ").append(newLine);
		for(int i=0; i < movie.getRatings().length; ++i){
			str.append(i+1).append(" : ").append(movie.getRatings()[i]).append(newLine);
		}
		str.append("average: ").append(movie.getAverageRating());
		return str.toString();
	}

	@Override
	public String toHtmlString() {
		HtmlPage html = new HtmlPage()
				.addDoctype("html")
				.openTag("html")
				.openTag("head")
				.addElement(new HtmlElement("title", movie.getTitle()))
				.addContent(StaticResources.getStyles())
				.addContent(StaticResources.getIcon())
				.closeTag("head")
				.openTag("body")
				.openTag(new HtmlElement("div").withAttribute("class", "container"))
				.addContent(CommonHtml.getHeader())
				.addContent(CommonHtml.getNavBar())
				.openTag(new HtmlElement("div").withAttribute("class", "content"))
				.openTag(new HtmlElement("div").withAttribute("class", "main"))
				// title
				.openTag("h1")
				.addElement(new HtmlElement("a", movie.getTitle())
						.withAttribute("href", "/movies/" + movie.getId()))
				.closeTag("h1")
				.openTag("hr")
				// movie info section
				.addElement(new HtmlElement("h3", "Movie Info"))
				.openTag("ul")
				.openTag("li")
				.addElement(new HtmlElement("b", "Release Year: "))
				.addContent(String.valueOf(movie.getReleaseYear()))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("b", "Average Rating: "))
				.addContent(movie.getAverageRatingRepresentation())
				.closeTag("li")
				.closeTag("ul")
				.openTag("hr")
				// Ratings details
				.addElement(new HtmlElement("h3", "Ratings details"))
				.openTag(new HtmlElement("table").withAttribute("id", "hor-minimalist-b"))
				.openTag(new HtmlElement("thead"))
				.openTag(new HtmlElement("tr"))
				.addElement(new HtmlElement("th", "Rating").withAttribute("align", "center"))
				.addElement(new HtmlElement("th", "Votes").withAttribute("align", "center"))
				.closeTag("tr")
				.closeTag("thead")
				.openTag("tbody");
		int[] ratings = movie.getRatings();
		for (int i=0; i < ratings.length; ++i) {
			html	.openTag("tr")
					.addElement(new HtmlElement("td", getRatingRepresentation(i+1)).withAttribute("align", "center"))
					.addElement(new HtmlElement("td", String.valueOf(ratings[i])).withAttribute("align", "center"))
					.closeTag("tr");
		}
		html	.closeTag("tbody")
				.closeTag("table")
				.openTag("hr")
				// Form
				.addElement(new HtmlElement("h3", "Add Rating"))
				.openTag(new HtmlElement("form").withAttribute("method", "POST"))
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "number")
						.withAttribute("min", "1")
						.withAttribute("max", "5")
						.withAttribute("name", "rating")
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

	private String getRatingRepresentation(int val){
		String res = "*";
		while(--val > 0)
			res += "*";
		return res;
	}
}
