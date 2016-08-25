package pt.isel.ls.models.domain.commands.components;

import pt.isel.ls.models.domain.request.components.IRequestComponent;

public interface ICommandComponent {

	public boolean validates(IRequestComponent rc);
}
