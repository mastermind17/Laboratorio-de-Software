package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.utils.Utils;

import java.util.List;

public class HeaderRestrictions {

	private String name;
	private boolean required;
	/**
	 * If no correct value is present its used the default.
	 * Null indicates no default value;
	 */
	private Object defaultValue;
	/**
	 * Indicates the parameter type.
	 */
	private Class type;
	/**
	 * Values allowed for the parameter.
	 * Null indicates any value is allowed.
	 */
	private List allowedValues;

	public HeaderRestrictions(String name, boolean required, Object defaultValue, Class type, List allowedValues){
		this.name = name;
		this.required = required;
		this.defaultValue = defaultValue;
		this.type = type;
		this.allowedValues = allowedValues;
	}

	public boolean validates(String param) {
		return !(type.equals(Integer.class) && param != null && !Utils.isInteger(param)) &&
				(!required || param != null && !(defaultValue == null && !allowedValues.contains(param)));
	}

	public String getName() { return name; }
	public Object getType() { return type; }
	public boolean isRequired() { return required; }
	public List getAllowedValues() { return allowedValues; }
	public Object getDefaultValue() { return  defaultValue; }
}
