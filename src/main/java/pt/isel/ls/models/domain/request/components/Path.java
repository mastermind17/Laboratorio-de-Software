package pt.isel.ls.models.domain.request.components;

import java.util.Arrays;
import java.util.LinkedList;

public class Path extends LinkedList<String> implements IRequestComponent {

	public Path(String pathStr){
		super(Arrays.asList(pathStr.substring(1).split("/")));
	}

}
