package pt.isel.ls.models.domain.request.components;

import java.util.*;

public class Headers extends HashMap<String,List<String>> implements IRequestComponent {

	public Headers(String headerStr){
		if(headerStr.length() == 0)	return;
		String[] pairs = headerStr.split("\\|");
		for(String pair : pairs){
			int idx = pair.indexOf(':');
			if(idx == -1)	continue;
			String key = pair.substring(0, idx);
			if(key.equals("")) continue;
			idx = pair.indexOf(':')+1;
			if(idx == -1)	continue;
			String value = pair.substring(idx);
			if(value.equals("")) continue;
			//handle spaces
			value = value.replaceAll("\\+", " ");
			this.put(key, Collections.singletonList(value));
		}
	}

	public Headers(Map<String,List<String>> param){
		for(Entry<String,List<String>> entry : param.entrySet()){
			this.put(entry.getKey(), entry.getValue());
		}
	}

	public static Headers valueOf(String headersStr){
		return null;
		//TODO não é usado
	}
}
