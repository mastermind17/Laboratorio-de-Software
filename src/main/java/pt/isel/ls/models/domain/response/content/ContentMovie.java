package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.models.mappers.CollectionMapper;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

import java.sql.SQLException;
import java.util.List;

public class ContentMovie implements IContent {

	private Movie movie;

	public ContentMovie(Movie movie){
		this.movie = movie;
	}

	@Override
	public String toPlainString() {
		String ls = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder()
				.append("--- Movie ---").append(ls).append(ls)
				.append("Title:").append(movie.getTitle()).append(ls)
				.append("Release Year: ").append(movie.getReleaseYear()).append(ls)
				.append("Average Rating: ").append(movie.getAverageRating()).append(ls).append(ls)
				.append("Movie Reviews").append(ls).append(ls);
		for (Review review : movie.getReviews()){
			builder
					.append(review.getSummary()).append(ls)
					.append("Rating: ").append(review.getRating()).append(ls)
					.append("Author: ").append(review.getCritic()).append(ls).append(ls);
		}
		if(movie.getReviews().size() == 0)
			builder.append("No reviews to show").append(ls);
		builder.append("Movie Collections").append(ls).append(ls);
		List<MovieCollection> collections = null;
		try {
			collections = CollectionMapper.getAllOfMovie(movie.getId());
		} catch (SQLException e) {
			//throw new RuntimeException("SQL BOOM!!");
			builder.append("SQL error. Tenta outra vez.").append(ls);
		}
		for (MovieCollection collection : collections)
			builder.append(collection.getName()).append(ls);
		if(collections.size() == 0)
			builder.append("No collections to show").append(ls);
		return builder.toString();
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
				.addElement(new HtmlElement("h1", movie.getTitle()))
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
				.addElement(new HtmlElement("a", "See rating details")
						.withAttribute("href", "/movies/" + movie.getId() + "/ratings"))
				.openTag("hr")
				.openTag("h3")
				.addElement(new HtmlElement("a", "Movie Reviews")
						.withAttribute("href", "/movies/" + movie.getId() + "/reviews?skip=0&top=5"))
				.closeTag("h3");
		// list of reviews section
		for (Review review : movie.getReviews()) {
			html
					.openTag(new HtmlElement("ul").withAttribute("class", "list-unstyled"))
					.addElement(new HtmlElement("li", review.getSummary()))
					.openTag("li")
					.addElement(new HtmlElement("b", "Rating: "))
					.addContent(review.getRatingRepresentation())
					.closeTag("li")
					.openTag("li")
					.addElement(new HtmlElement("b", "Author: "))
					.addContent(review.getCritic())
					.closeTag("li")
					.openTag("li")
					.addElement(new HtmlElement("a", "See full review")
							.withAttribute("href", "/movies/" + movie.getId() + "/reviews/" + review.getId()))
					.closeTag("li")
					.closeTag("ul");
		}
		if(movie.getReviews().size() == 0)
			html.addElement(new HtmlElement("li", "No reviews to show"));
		// list of collections section
		html	.openTag("hr")
				.addElement(new HtmlElement("h3", "Movie Collections"))
				.openTag("ul");
		List<MovieCollection> collections;
		try {
			collections = CollectionMapper.getAllOfMovie(movie.getId());
		} catch (SQLException e) {
			// TODO não rebentar com o programa, mostrar mensagem de erro
			throw new RuntimeException("SQL BOOM!!");
		}
		for (MovieCollection collection : collections) {
			html	.openTag("li")
					.addElement(new HtmlElement("a", collection.getName())
							.withAttribute("href", "/collections/" + collection.getId()))
					.closeTag("li");
		}
		if(collections.size() == 0)
			html.addElement(new HtmlElement("li", "No collections to show"));
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
