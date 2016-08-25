package pt.isel.ls.models.domain.response.content;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.utils.html.CommonHtml;
import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;
import pt.isel.ls.utils.resources.StaticResources;

public class ContentReview implements IContent {

	private pt.isel.ls.models.domain.movies.Review review;
	private Movie movie;

	public ContentReview(Movie movie, pt.isel.ls.models.domain.movies.Review review) {
		this.movie = movie;
		this.review = review;
	}

	@Override
	public String toPlainString() {
		String ls = System.getProperty("line.separator");
		return new StringBuilder()
				.append("Movie: ").append(movie.getTitle()).append(ls)
				.append("Reviewer name: ").append(review.getCritic()).append(ls)
				.append("Summary: ").append(review.getSummary()).append(ls)
				.append("Rating: ").append(review.getRating()).append(ls)
				.append("Content: ").append(review.getContent())
				.toString();
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
				.openTag("h3")
				.addElement(new HtmlElement("a", "Movie Reviews")
						.withAttribute("href", "/movies/" + movie.getId() + "/reviews?skip=0&top=5"))
				.closeTag("h3")
				.openTag("hr")
				.addElement(new HtmlElement("h3", review.getSummary()))
				.openTag(new HtmlElement("ul").withAttribute("class", "list-unstyled"))
				.openTag("li")
				.addElement(new HtmlElement("b", "Rating: "))
				.addContent(review.getRatingRepresentation())
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("b", "Author: "))
				.addContent(review.getCritic())
				.closeTag("li")
				.closeTag("ul")
				.addElement(new HtmlElement("p", review.getContent()))
				// Page end
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
