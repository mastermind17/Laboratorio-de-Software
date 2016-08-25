package pt.isel.ls.models.domain.request.components;

import java.util.HashMap;
import java.util.Map;

public class Parameters extends HashMap<String,String> implements IRequestComponent {

	public Parameters(String paramStr){
		if(paramStr.length() == 0)	return;
		String[] pairs = paramStr.split("&");
		for(String pair : pairs){
			int idx = pair.indexOf('=');
			if(idx == -1)	continue;
			String key = pair.substring(0, idx);
			if(key.equals("")) continue;
			idx = pair.indexOf('=')+1;
			if(idx == -1)	continue;
			String value = pair.substring(idx);
			if(value.equals("")) continue;
			//handle spaces
			value = value.replaceAll("\\+", " ");
			this.put(key, value);
		}
	}

    public Parameters(Map<String,String[]> param){
        for(Entry<String,String[]> entry : param.entrySet())
            this.put(entry.getKey(), entry.getValue()[0]);
    }

	public static Parameters valueOf(String paramStr){
		return null;
		//TODO não é usado
	}
}
