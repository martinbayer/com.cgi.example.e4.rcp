package c3_commands_handlers.parts;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class PartSaveHandler {
	@Execute
	public void execute(Shell shell, EPartService service, MPart part) {
		MessageDialog.openInformation(shell, "Object store", "Person stored");
		service.savePart(part, false);
	}

}