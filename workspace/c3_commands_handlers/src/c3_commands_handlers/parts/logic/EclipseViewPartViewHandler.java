package c3_commands_handlers.parts.logic;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class EclipseViewPartViewHandler {
	@Execute
	public void execute(
			Shell shell,
			@Preference(nodePath = "c3_commands_handlers") IEclipsePreferences prefs) {
		String firstName = prefs.get("firstName", "UNKNOWN FIRST NAME");
		String lastName = prefs.get("lastName", "UNKNOWN LAST NAME");
		String email = prefs.get("email", "UNKNOWN EMAIL");
		MessageDialog.openInformation(shell,
				"Last person stored in eclipse properties", "Name: "
						+ firstName + " " + lastName + "\n Email:" + email);
	}

}