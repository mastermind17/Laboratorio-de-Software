package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.models.domain.request.components.IRequestComponent;
import pt.isel.ls.models.domain.request.components.Parameters;

import java.util.HashMap;
import java.util.List;

public class ParametersTemplate extends HashMap<String, ParameterRestrictions> implements ICommandComponent {

	public ParametersTemplate(List<ParameterRestrictions> params){
		params.forEach((p) -> this.put(p.getName(), p));
	}

	@Override
	public boolean validates(IRequestComponent rc){
		Parameters params = (Parameters) rc;
		if(params == null)
			return false;
		for(ParameterRestrictions pr : this.values()){
			if(!pr.validates(params.get(pr.getName())))
				return false;
		}
		return true;
	}
}
