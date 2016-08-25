package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.models.domain.request.components.IRequestComponent;
import pt.isel.ls.models.domain.request.components.Method;

public class MethodTemplate implements ICommandComponent {

	private String value;

	public MethodTemplate(String v){
		value = v;
	}

	public String getValue() { return value; }

	@Override
	public boolean validates(IRequestComponent rc){
		Method other = (Method)rc;
		return value.equals(other.getValue());
	}
}
