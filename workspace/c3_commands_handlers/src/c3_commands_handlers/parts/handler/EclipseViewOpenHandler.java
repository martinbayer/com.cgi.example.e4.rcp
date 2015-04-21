package c3_commands_handlers.parts.handler;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class EclipseViewOpenHandler {
	@Execute
	public void execute(
			Shell shell,
			@Preference(nodePath = "c3_commands_handlers") IEclipsePreferences prefs) {
		String firstName = prefs.get("firstName", "unknown");
		String lastName = prefs.get("lastName", "unknown");
		String email = prefs.get("email", "unknown");
		String message = String.format(
				"First name: %s \nLast name: %s \nEmail: %s", firstName,
				lastName, email);
		MessageDialog
				.openInformation(shell, "Last stored information", message);
	}

}