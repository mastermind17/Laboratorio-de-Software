package pt.isel.ls.utils.resources;

import pt.isel.ls.utils.html.HtmlElement;
import pt.isel.ls.utils.html.HtmlPage;

public class StaticResources {

	public static final String LEFT_ARROW_URL = "https://googledrive.com/host/0B6yWzZVa85EMa2ZQN1ozUjAtbjQ/images/arrows/left_arrow.png";
	public static final String RIGHT_ARROW_URL = "https://googledrive.com/host/0B6yWzZVa85EMa2ZQN1ozUjAtbjQ/images/arrows/right_arrow.png";
	private static final String ICON_URL = "https://googledrive.com/host/0B6yWzZVa85EMa2ZQN1ozUjAtbjQ/images/icons/slb_icon.png";
	private static final String STYLESHEET_URL = "https://googledrive.com/host/0B6yWzZVa85EMa2ZQN1ozUjAtbjQ/css/styles.css";
	private static final String SORT_MOVIES_TABLE_SCRIPT_URL = "https://googledrive.com/host/0B6yWzZVa85EMa2ZQN1ozUjAtbjQ/js/sortMoviesTable.js";

	public static String getStyles(){
		return new HtmlPage()
				.openTag(new HtmlElement("link")
						.withAttribute("rel", "stylesheet")
						.withAttribute("type", "text/css")
						.withAttribute("href", STYLESHEET_URL))
				.toString();
	}

	public static String getSortMoviesTableScript(){
		return new HtmlPage()
				.addElement(new HtmlElement("script").withAttribute("src", SORT_MOVIES_TABLE_SCRIPT_URL))
				.toString();
	}

	public static String getIcon(){
		return new HtmlPage()
				.openTag(new HtmlElement("link")
						.withAttribute("rel", "icon")
						.withAttribute("href", ICON_URL))
				.toString();
	}
}
