package c3_commands_handlers.parts.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import c3_commands_handlers.parts.logic.PersonDBStore;
import c3_commands_handlers.parts.model.Person;

public class DBViewPartOpenHandler {
	@Execute
	public void execute(Shell shell, @Optional PersonDBStore personStore) {
		if (personStore == null) {
			MessageDialog.openInformation(shell, "No person store found",
					"Unable to open DB store for persons");
			return;
		}
		int countOfPersons = personStore.getObjects().size();
		StringBuffer sb = new StringBuffer();
		sb.append("Printing ").append(countOfPersons).append(" persons:\n");
		for (Person p : PersonDBStore.INSTANCE.getObjects()) {
			sb.append("First name: ").append(p.getFirstName()).append("\n");
			sb.append("Last name: ").append(p.getLastName()).append("\n");
			sb.append("Email: ").append(p.getEmail()).append("\n");
			sb.append("======================================================\n\n");
		}
		MessageBox d = new MessageBox(shell);
		d.setMessage(sb.toString());
		d.open();
	}
}