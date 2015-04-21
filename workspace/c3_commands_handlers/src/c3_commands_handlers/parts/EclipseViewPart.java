package c3_commands_handlers.parts;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.dialogs.MessageDialog;
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
import c3_commands_handlers.parts.model.Person;

public class EclipseViewPart {

	private Label firstNameLabel, lastNameLabel, emailLabel;
	private Text firstNameInput, lastNameInput, emailInput;

	@Inject
	private EHandlerService handlerService;
	@Inject
	private ECommandService commandService;
	@Inject
	private MDirtyable dirty;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		/* first name */
		firstNameLabel = new Label(parent, SWT.NONE);
		firstNameLabel.setText("First name:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		firstNameLabel.setLayoutData(gridData);

		firstNameInput = new Text(parent, SWT.BORDER);
		firstNameInput.addModifyListener(new EditFieldDirtyListener(dirty));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		firstNameInput.setLayoutData(gridData);

		/* last name */
		lastNameLabel = new Label(parent, SWT.NONE);
		lastNameLabel.setText("Last name:");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		lastNameLabel.setLayoutData(gridData);
		lastNameInput = new Text(parent, SWT.BORDER);
		lastNameInput.addModifyListener(new EditFieldDirtyListener(dirty));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		lastNameInput.setLayoutData(gridData);

		/* email name */
		emailLabel = new Label(parent, SWT.NONE);
		emailLabel.setText("Email:");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		emailLabel.setLayoutData(gridData);
		emailInput = new Text(parent, SWT.BORDER);
		emailInput.addModifyListener(new EditFieldDirtyListener(dirty));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		emailInput.setLayoutData(gridData);

		Button openBtn = new Button(parent, SWT.NONE);
		openBtn.setText("Show persons");
		openBtn.addSelectionListener(new ShowDBRecords(handlerService,
				commandService));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		openBtn.setLayoutData(gridData);

	}

	@Focus
	public void setFocus() {
		firstNameInput.setFocus();
	}

	@Persist
	public void save(
			Shell shell,
			@Preference(nodePath = "c3_commands_handlers") IEclipsePreferences prefs) {
		Person p = new Person();
		p.setFirstName(firstNameInput.getText());
		p.setLastName(lastNameInput.getText());
		p.setEmail(emailInput.getText());
		if (prefs == null) {
			MessageDialog.openInformation(shell, "Unable to store object",
					"Unable to store person " + p);
		} else {
			prefs.put("firstName", p.getFirstName());
			prefs.put("lastName", p.getLastName());
			prefs.put("email", p.getEmail());
		}
		dirty.setDirty(false);
	}
}

class ShowEclipsePrefs extends SelectionAdapter {

	private EHandlerService handlerService;
	private ECommandService commandService;

	public ShowEclipsePrefs(EHandlerService handlerService,
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