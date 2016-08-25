package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.models.domain.request.components.Headers;
import pt.isel.ls.models.domain.request.components.IRequestComponent;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a Request headers.
 * The maps holds all the valid headers.
 */
public class HeadersTemplate extends HashMap<String, HeaderRestrictions> implements ICommandComponent {

	public HeadersTemplate(List<HeaderRestrictions> hs){
		hs.forEach((h) -> this.put(h.getName(), h));
	}

	@Override
	public boolean validates(IRequestComponent rc) {
		Headers headers = (Headers) rc;
		if(headers == null)
			return false;
		for(HeaderRestrictions hr : this.values()){
			// if the request doesn't have this header, check if no value is acceptable
			if(headers.get(hr.getName()) == null){
				if(!hr.validates(null))
					return false;
			}
			else
				// otherwise check the given value
				if(!hr.validates(headers.get(hr.getName()).get(0)))
					return false;
		}
		// all headers are valid
		return true;
	}
}
