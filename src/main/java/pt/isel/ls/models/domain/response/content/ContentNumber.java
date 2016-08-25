package pt.isel.ls.models.domain.response.content;

public class ContentNumber implements IContent {

	private java.lang.Integer value;

	public ContentNumber(int i){
		value = i;
	}

	@Override
	public String toPlainString() {
		return value.toString();
	}

	@Override
	public String toHtmlString() { return value.toString(); }
}
