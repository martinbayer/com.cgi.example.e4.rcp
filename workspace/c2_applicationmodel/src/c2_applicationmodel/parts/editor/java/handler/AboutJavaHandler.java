 
package c2_applicationmodel.parts.editor.java.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class AboutJavaHandler {
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "About Java", "Java editor example.");
	}
		
}