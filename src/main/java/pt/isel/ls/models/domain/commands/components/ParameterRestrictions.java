package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.utils.Utils;

import java.util.List;

/**
 *
 */
public class ParameterRestrictions {

	private String name;
	private boolean required;
	/**
	 * Indicates the parameter type.
	 */
	private Class type;
	/**
	 * Values allowed for the parameter.
	 * Null indicates any value is allowed.
	 */
	private List<Object> allowedValues;

	public ParameterRestrictions(String name, boolean required, Class type, List<Object> allowedValues){
		this.name = name;
		this.required = required;
		this.type = type;
		this.allowedValues = allowedValues;
	}

	public boolean validates(String param) {
		// check type
		if(type.equals(Integer.class) && param != null && !Utils.isInteger(param))
				return false;
		if(required && param == null)
			return false;
		if(required && allowedValues == null)
			return true;
		if(required && allowedValues.contains(param))
			return true;
		if(required && !allowedValues.contains(param))
			return false;
		if(!required && param == null)
			return true;
		if(!required && allowedValues == null)
			return true;
		if(!required && allowedValues.contains(param))
			return true;
		if(!required && !allowedValues.contains(param))
			return false;
		return false;
	}

	public String getName() { return name; }
	public Object getType() { return type; }
	public boolean isRequired() { return required; }
	public List<Object> getAllowedValues() { return allowedValues; }
}