package pt.isel.ls.utils.html;

/**
 * Utility class with common Html code.
 */
public class CommonHtml {

	public static String getHeader(){
		return new HtmlPage()
				.openTag(new HtmlElement("div").withAttribute("class", "header"))
				.addElement(new HtmlElement("h1", "LS PROJECT").withAttribute("class", "header-heading"))
				.closeTag("div")
				.toString();
	}

	public static String getNavBar(){
		return new HtmlPage()
				.openTag(new HtmlElement("div").withAttribute("class", "nav-bar"))
				.openTag(new HtmlElement("ul").withAttribute("class", "nav"))
				.openTag("li")
				.addElement(new HtmlElement("a", "Home").withAttribute("href", "/"))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("a", "Movies").withAttribute("href", "/movies?skip=0&top=5"))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("a", "Collections").withAttribute("href", "/collections?skip=0&top=5"))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("a", "Tops").withAttribute("href", "/tops/ratings"))
				.closeTag("li")
				.openTag("li")
				.addElement(new HtmlElement("a", "About").withAttribute("href", "/about"))
				.closeTag("li")
				.closeTag("ul")
				.closeTag("div")
				.toString();
	}

	public static String getFooter(){
		return new HtmlPage()
				.addElement(new HtmlElement("div", "Group 12").withAttribute("class", "footer"))
				.toString();
	}
}
