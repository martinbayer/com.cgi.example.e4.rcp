package c3_commands_handlers.parts.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class EclipseViewPartOpenHandler {
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell,
				"Person stored in Eclipse properties displayed",
				"Name: user's name\n Email: user's email");
	}

}