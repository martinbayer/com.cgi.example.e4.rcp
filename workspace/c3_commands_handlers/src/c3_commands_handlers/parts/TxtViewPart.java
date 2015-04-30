package c3_commands_handlers.parts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TxtViewPart {
	to asi nema smysl...zkontrolovat
	na zacatku ukazat, ze view se chova jinak pro kazdou partu a ukazat k tomu applikacni model. kazdy handler ma jiny kod
	private Label textContentLabel;
	private Text textContentInput;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		/* Text */
		textContentLabel = new Label(parent, SWT.NONE);
		textContentLabel.setText("Text file content:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		textContentLabel.setLayoutData(gridData);

		textContentInput = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		textContentInput.setLayoutData(gridData);

	}

	@Focus
	public void setFocus() {
		textContentInput.setFocus();
	}

	public void setText(Path txtFile) {

		try {
			String content = new String(Files.readAllBytes(txtFile));
			textContentInput.setText(content);
			textContentInput.update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}