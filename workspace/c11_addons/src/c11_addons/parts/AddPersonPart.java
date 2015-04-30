package c11_addons.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.event.Event;

import c11_addons.AddonsEvents;
import c11_addons_storage.model.Person;
import c11_addons_storage_DB.PersonDBStorage;

public class AddPersonPart {
	prepsat popisky u radio buttonu
	poradne popsat
	private Label firstNameLabel, lastNameLabel, emailLabel, ageLabel;
	private Text firstNameInput, lastNameInput, emailInput, ageInput;
	private Text status;
	private Button addPersonBtn, updatePersonBtn, deletePersonBtn;

	@Inject
	@Optional
	private PersonDBStorage personStorage;

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
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		emailInput.setLayoutData(gridData);

		/* age name */
		ageLabel = new Label(parent, SWT.NONE);
		ageLabel.setText("Age:");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		ageLabel.setLayoutData(gridData);
		ageInput = new Text(parent, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		ageInput.setLayoutData(gridData);

		createRadioBtns(parent);

		Button processPersonBtn = new Button(parent, SWT.NONE);
		processPersonBtn.setText("Process person");
		processPersonBtn.addSelectionListener(new ProcessRequest(this));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		processPersonBtn.setLayoutData(gridData);

		status = new Text(parent, SWT.BORDER);
		status.setEditable(false);
		status.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		status.setLayoutData(gridData);
	}

	private void createRadioBtns(Composite parent) {
		addPersonBtn = new Button(parent, SWT.RADIO);
		addPersonBtn.setText("Add person via synch event");

		updatePersonBtn = new Button(parent, SWT.RADIO);
		updatePersonBtn.setText("Add person via asynch event");

		deletePersonBtn = new Button(parent, SWT.RADIO);
		deletePersonBtn.setText("Add person to context");
	}

	public void processPerson() {
		Person p = createPersonFromForm();
		if (addPersonBtn.getSelection()) {
			personStorage.insert(p);
		} else if (updatePersonBtn.getSelection()) {
			personStorage.update(p);
		} else if (deletePersonBtn.getSelection()) {
			personStorage.delete(p);
		}
	}

	private Person createPersonFromForm() {
		Person p = new Person();
		p.setEmail(emailInput.getText());
		p.setName(firstNameInput.getText() + " " + lastNameInput.getText());
		return p;
	}

	@Focus
	public void setFocus() {
		firstNameInput.setFocus();
	}

	@Inject
	@Optional
	public void reconnect(
			@UIEventTopic(value = AddonsEvents.PERSON_STORAGE_RECONNECT) Event event) {
		if (AddonsEvents.PERSON_STORAGE_RECONNECT_SUCCESFULL.equals(event
				.getTopic())) {
			status.setText("Storage reconnected");
		} else if (AddonsEvents.PERSON_STORAGE_RECONNECT_FAILED.equals(event
				.getTopic())) {
			status.setText("Storage reconnect failed");
		}
	}

	class ProcessRequest extends SelectionAdapter {

		private AddPersonPart part;

		public ProcessRequest(AddPersonPart part) {
			this.part = part;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			this.part.processPerson();
		}
	}
}