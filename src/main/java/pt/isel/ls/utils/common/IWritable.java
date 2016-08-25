package pt.isel.ls.utils.common;

import java.io.IOException;
import java.io.Writer;

public interface IWritable {
	void writeTo(Writer w) throws IOException;
}