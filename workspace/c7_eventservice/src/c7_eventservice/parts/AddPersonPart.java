/*******************************************************************************
 * Copyright (c) 2010 - 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package c7_eventservice.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import c7_eventservice.parts.logic.PersonEventConstants;
import c7_eventservice.parts.model.Person;

public class AddPersonPart {

	private Label nameLabel, emailLabel;
	private Text nameInput, emailInput;
	private Button addEvtBtn, deleteEvtBtn;

	private Button updateNameBtn, updateEmailBtn;

	@Inject
	private IEventBroker eventBroker;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		/* name */
		nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("Name:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		nameLabel.setLayoutData(gridData);

		nameInput = new Text(parent, SWT.BORDER);
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
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		emailInput.setLayoutData(gridData);

		createButtons(parent);

		updateNameBtn = new Button(parent, SWT.NONE);
		updateNameBtn.setText("Update name");
		updateNameBtn.addSelectionListener(new UpdateName(this));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		updateNameBtn.setLayoutData(gridData);

		updateEmailBtn = new Button(parent, SWT.NONE);
		updateEmailBtn.setText("Update email");
		updateEmailBtn.addSelectionListener(new UpdateEmail(this));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		updateEmailBtn.setLayoutData(gridData);
	}

	private void createButtons(Composite parent) {
		addEvtBtn = new Button(parent, SWT.NONE);
		addEvtBtn.setText("Add person event");
		addEvtBtn.addSelectionListener(new AddPerson(this));

		deleteEvtBtn = new Button(parent, SWT.NONE);
		deleteEvtBtn.setText("Delete person event");
		deleteEvtBtn.addSelectionListener(new DeletePerson(this));
	}

	public void addPerson() {
		System.out.println("Add event invoked");
		eventBroker.send(PersonEventConstants.TOPIC_PERSON_NEW,
				createPersonFromForm());
	}

	public void deletePerson() {
		System.out.println("Delete event invoked");
		eventBroker.send(PersonEventConstants.TOPIC_PERSON_DELETE,
				createPersonFromForm());
	}

	private Person createPersonFromForm() {
		Person p = new Person();
		p.setEmail(emailInput.getText());
		p.setName(nameInput.getText());
		return p;
	}

	@Focus
	public void setFocus() {
		nameInput.setFocus();
	}

	class AddPerson extends SelectionAdapter {

		private AddPersonPart part;

		public AddPerson(AddPersonPart part) {
			this.part = part;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			this.part.addPerson();
		}
	}

	class DeletePerson extends SelectionAdapter {

		private AddPersonPart part;

		public DeletePerson(AddPersonPart part) {
			this.part = part;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			this.part.deletePerson();
		}
	}

	class UpdateName extends SelectionAdapter {

		private AddPersonPart part;

		public UpdateName(AddPersonPart part) {
			this.part = part;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			this.part.updateName();
		}
	}

	class UpdateEmail extends SelectionAdapter {

		private AddPersonPart part;

		public UpdateEmail(AddPersonPart part) {
			this.part = part;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			this.part.updateEmail();
		}
	}

	public void updateName() {
		eventBroker.send(PersonEventConstants.TOPIC_PERSON_NAME_CHANGED,
				createPersonFromForm());
	}

	public void updateEmail() {
		eventBroker.send(PersonEventConstants.TOPIC_PERSON_EMAIL_CHANGED,
				createPersonFromForm());
	}
}