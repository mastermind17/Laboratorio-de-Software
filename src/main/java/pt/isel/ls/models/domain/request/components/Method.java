package pt.isel.ls.models.domain.request.components;

public class Method implements IRequestComponent {

	private String value;

	public Method(String v){
		value = v;
	}

	public String getValue() { return value; }
}
