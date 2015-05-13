package c3_commands_handlers.parts;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import c3_commands_handlers.parts.logic.EditFieldDirtyListener;

public class EclipseViewPart {

	private Label nameLabel;
	private Text nameInput;

	@Inject
	private EHandlerService handlerService;
	@Inject
	private ECommandService commandService;
	@Inject
	private MDirtyable dirty;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		/* name */
		nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("First name:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		nameLabel.setLayoutData(gridData);

		nameInput = new Text(parent, SWT.BORDER);
		nameInput.addModifyListener(new EditFieldDirtyListener(dirty));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		nameInput.setLayoutData(gridData);

		Button openBtn = new Button(parent, SWT.NONE);
		openBtn.setText("Show persons");
		openBtn.addSelectionListener(new ShowEclipsePrefs(handlerService,
				commandService));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		openBtn.setLayoutData(gridData);

	}

	@Focus
	public void setFocus() {
		nameInput.setFocus();
	}

	@Persist
	public void save(Shell shell) {
		System.out.println("Person stored to eclipse properties");
		dirty.setDirty(false);
	}
}

class ShowEclipsePrefs extends SelectionAdapter {

	private EHandlerService handlerService;
	private ECommandService commandService;

	ShowEclipsePrefs(EHandlerService handlerService,
			ECommandService commandService) {
		this.handlerService = handlerService;
		this.commandService = commandService;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		handlerService.executeHandler(commandService.createCommand(
				"c3_commands_handlers.open", Collections.emptyMap()));

	}
}