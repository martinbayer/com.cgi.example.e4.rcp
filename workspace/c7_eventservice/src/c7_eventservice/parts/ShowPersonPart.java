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

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
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
import c7_eventservice.parts.model.PersonStore;

public class ShowPersonPart {

	private Label firstNameLabel, lastNameLabel, emailLabel;
	private Text firstNameInput, lastNameInput, emailInput;
	private Text statusText;

	@Inject
	private Logger logger;

	@Inject
	private MApplication application;

	@PostConstruct
	public void createComposite(Composite parent, @Optional Person person) {
		parent.setLayout(new GridLayout(2, false));

		/* first name */
		firstNameLabel = new Label(parent, SWT.NONE);
		firstNameLabel.setText("First name:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		firstNameLabel.setLayoutData(gridData);

		firstNameInput = new Text(parent, SWT.BORDER);
		firstNameInput.setEditable(false);
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
		lastNameInput.setEditable(false);
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

		Button reloadContext = new Button(parent, SWT.NONE);
		reloadContext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displayPerson(application.getContext().get(Person.class));
			}
		});
		reloadContext.setText("Reload from Context");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		reloadContext.setLayoutData(gridData);
	}

	private void displayPerson(Person person) {
		if (person != null) {
			firstNameInput.setText(person.getFirstName());
			lastNameInput.setText(person.getLastName());
			emailInput.setText(person.getEmail());
		} else {
			firstNameInput.setText("");
			lastNameInput.setText("");
			emailInput.setText("");
		}
	}

	@Inject
	@Optional
	public void personEventReceived(
			@EventTopic(value = PersonEventConstants.TOPIC_PERSON_GENERAL) Person p) {
		// MessageDialog.openInformation(shell, "Something's going on...",
		// "...with person: " + p);
		logger.info("Something's going on with person " + p);
	}

	@Inject
	@Optional
	public void personAddedEvent(
			@UIEventTopic(value = PersonEventConstants.TOPIC_PERSON_NEW) Person p) {
		logger.info("Person is going to be added to the storage. " + p);
		boolean result = PersonStore.INSTANCE.addPerson(p);
		displayPerson(p);
		if (result) {
			statusText.setText("Person added to the storage");
			logger.info("Person added to the storage.");
		} else {
			statusText.setText("Person is already in the storage");
			logger.info("Person is already in the storage.");
		}
	}

	@Inject
	@Optional
	public void personDeletedEvent(
			@UIEventTopic(value = PersonEventConstants.TOPIC_PERSON_DELETE) Person p) {
		logger.info("Person is going to be removed from the storage. " + p);
		displayPerson(p);
		boolean result = PersonStore.INSTANCE.removePerson(p);
		if (result) {
			statusText.setText("Person removed from the storage");
			logger.info("Person removed from the storage.");
		} else {
			statusText.setText("Person not found in the storage");
			logger.info("Person not found in the storage.");
		}
	}

	@Focus
	public void setFocus() {
		firstNameInput.setFocus();
	}

}
