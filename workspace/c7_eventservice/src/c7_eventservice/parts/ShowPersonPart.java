package c7_eventservice.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import c7_eventservice.parts.logic.PersonEventConstants;
import c7_eventservice.parts.model.Person;
import c7_eventservice.parts.model.PersonStore;

public class ShowPersonPart {

	private Label nameLabel, emailLabel;
	private Text nameInput, emailInput;
	private Text statusText;

	@PostConstruct
	public void createComposite(Composite parent, @Optional Person person) {
		parent.setLayout(new GridLayout(2, false));

		/* name */
		nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("First name:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		nameLabel.setLayoutData(gridData);

		nameInput = new Text(parent, SWT.BORDER);
		nameInput.setEditable(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		nameInput.setLayoutData(gridData);

		/* email name */
		emailLabel = new Label(parent, SWT.NONE);
		emailLabel.setText("Email:");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		emailLabel.setLayoutData(gridData);
		emailInput = new Text(parent, SWT.BORDER);
		emailInput.setEditable(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		emailInput.setLayoutData(gridData);

		Label statusLabel = new Label(parent, SWT.NONE);
		statusLabel.setText("Status");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		statusLabel.setLayoutData(gridData);

		statusText = new Text(parent, SWT.BORDER);
		statusText.setEditable(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		statusText.setLayoutData(gridData);
		displayPerson(person);
	}

	private void displayPerson(Person person) {
		if (person != null) {
			nameInput.setText(person.getName());
			emailInput.setText(person.getEmail());
		} else {
			nameInput.setText("");
			emailInput.setText("");
		}
	}

	@Inject
	@Optional
	public void personEventReceived(
			@EventTopic(value = PersonEventConstants.TOPIC_PERSON_GENERAL) Person p) {
		System.out.println("Something's going on with person " + p);
	}

	@Inject
	@Optional
	public void personAddedEvent(
			@UIEventTopic(value = PersonEventConstants.TOPIC_PERSON_NEW) Person p) {
		System.out.println("Person is going to be added to the storage. " + p);
		boolean result = PersonStore.INSTANCE.addPerson(p);
		displayPerson(p);
		if (result) {
			statusText.setText("Person added to the storage");
			System.out.println("Person added to the storage.");
		} else {
			statusText.setText("Person is already in the storage");
			System.out.println("Person is already in the storage.");
		}
	}

	@Inject
	@Optional
	public void personDeletedEvent(
			@UIEventTopic(value = PersonEventConstants.TOPIC_PERSON_DELETE) Person p) {
		System.out.println("Person is going to be removed from the storage. "
				+ p);
		displayPerson(null);
		boolean result = PersonStore.INSTANCE.removePerson(p);
		if (result) {
			statusText.setText("Person removed from the storage");
			System.out.println("Person removed from the storage.");
		} else {
			statusText.setText("Person not found in the storage");
			System.out.println("Person not found in the storage.");
		}
	}

	@Focus
	public void setFocus() {
		nameInput.setFocus();
	}

}
