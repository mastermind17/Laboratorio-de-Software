package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.models.domain.request.components.IRequestComponent;
import pt.isel.ls.models.domain.request.components.Path;

import java.util.*;

public class PathTemplate extends LinkedList<String> implements ICommandComponent {

	public PathTemplate(String pathStr){
		super(Arrays.asList(pathStr.substring(1).split("/")));
	}

	@Override
	public boolean validates(IRequestComponent rc) {
		Path path = (Path)rc;
		if(path == null)
			return false;
		List<?> other = (List<?>) path;
		if(this.size() != other.size())
			return false;
		for(int i=0; i < this.size(); ++i){
			if(this.get(i).startsWith("{") && this.get(i).endsWith("}"))
				continue;
			if(!this.get(i).equals(other.get(i)))
				return false;

		}
		return true;
	}

	private class Segment {

		private String name;

		public Segment(String n){
			name = n;
		}

		public boolean match(String str){
			return name.equals(str);
		}
	}
}
