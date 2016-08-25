package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

import java.util.List;

public class ContentReviewsSamples implements IContent {

	private List<Review> reviews;
	private Movie movie;

	public ContentReviewsSamples(Movie movie, List<Review> reviews){
		this.movie = movie;
		this.reviews = reviews;
	}

	@Override
	public String toPlainString() {
		String ls = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder
				.append("Movie: ").append(movie.getTitle()).append(ls).append(ls)
				.append("Movie Reviews").append(ls).append(ls);
		reviews.forEach( review -> builder.append(review.toString()).append(ls) );
		if(reviews.size() == 0)
			builder.append("No reviews to show.").append(ls);
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
				// title
				.openTag("h1")
				.addElement(new HtmlElement("a", movie.getTitle())
						.withAttribute("href", "/movies/" + movie.getId()))
				.closeTag("h1")
				.openTag("hr")
				.addElement(new HtmlElement("h3", "Movie Reviews"));
		// list of reviews section
		List<Review> reviews = movie.getReviews();
		for (Review review : reviews) {
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
		if(reviews.size() == 0){
			html	.openTag("br")
					.addElement(new HtmlElement("p", "No reviews to show."));
		}
		html	.openTag("hr")
				// Form
				.addElement(new HtmlElement("h3", "Add Review"))
				.openTag(new HtmlElement("form").withAttribute("method", "POST"))
				.addContent("Author:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "reviewerName")
						.withAttribute("required", ""))
				.openTag("br")
				.addContent("Summary:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "reviewSummary")
						.withAttribute("required", ""))
				.openTag("br")
				.addContent("Rating:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "number")
						.withAttribute("min", "1")
						.withAttribute("max", "5")
						.withAttribute("name", "rating")
						.withAttribute("required", ""))
				.openTag("br")
				.addContent("Content:")
				.openTag("br")
				.openTag(new HtmlElement("input")
						.withAttribute("type", "text")
						.withAttribute("name", "review")
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
