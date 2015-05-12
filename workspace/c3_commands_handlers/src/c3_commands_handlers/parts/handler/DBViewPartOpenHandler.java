package c3_commands_handlers.parts.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DBViewPartOpenHandler {
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "Persons stored in DB",
				"Persons stored in DB could be printed out here");
		return;
	}
}