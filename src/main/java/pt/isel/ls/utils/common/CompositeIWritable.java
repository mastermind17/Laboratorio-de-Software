package pt.isel.ls.utils.common;

import java.io.IOException;
import java.io.Writer;

public final class CompositeIWritable implements IWritable {

	private final IWritable[] _content;

	public CompositeIWritable(IWritable... cs){
		_content = cs;
	}
	@Override
	public void writeTo(Writer w) throws IOException {
		for(IWritable c : _content) {
			c.writeTo(w);
		}
	}
}