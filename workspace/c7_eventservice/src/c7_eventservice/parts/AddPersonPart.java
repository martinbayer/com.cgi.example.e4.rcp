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
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.Focus;
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

public class AddPersonPart {
	// radsi pouzit sysout misto logovani - je to hezcejsiii
	dat do pryc jedno policko a pridat topicy namechanged a emailchanged a ve druhe parte nebo ve status baru bude informace o tom co se zmenilo (email/name)
	v dalsi part bude informace o persone a ukazat na tom, ze kdyz neni part iniciovana "neslysi" eventy
	zrusit sync/async, protoze to kazdy vi

	@Inject
	private Logger logger;

	private Label firstNameLabel, lastNameLabel, emailLabel;
	private Text firstNameInput, lastNameInput, emailInput;
	private Button addSyncEvtBtn, addAsyncEvtBtn, addCtxBtn, deleteSyncEvtBtn,
			deleteAsyncEvtBtn, deleteCtxBtn;

	@Inject
	private MApplication application;

	@Inject
	private IEventBroker eventBroker;

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

		createCheckBoxes(parent);

		Button processPersonBtn = new Button(parent, SWT.NONE);
		processPersonBtn.setText("Process person");
		processPersonBtn.addSelectionListener(new ProcessRequest(this));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		processPersonBtn.setLayoutData(gridData);
	}

	private void createCheckBoxes(Composite parent) {
		addSyncEvtBtn = new Button(parent, SWT.CHECK);
		addSyncEvtBtn.setText("Add person via synch event");

		addAsyncEvtBtn = new Button(parent, SWT.CHECK);
		addAsyncEvtBtn.setText("Add person via asynch event");

		addCtxBtn = new Button(parent, SWT.CHECK);
		addCtxBtn.setText("Add person to context");

		deleteSyncEvtBtn = new Button(parent, SWT.CHECK);
		deleteSyncEvtBtn.setText("Delete person via synch event");

		deleteAsyncEvtBtn = new Button(parent, SWT.CHECK);
		deleteAsyncEvtBtn.setText("Delete person via asynch event");

		deleteCtxBtn = new Button(parent, SWT.CHECK);
		deleteCtxBtn.setText("Delete person from context");

	}

	public void processPerson() {
		Person p = createPersonFromForm();
		boolean adding = false;
		if (addSyncEvtBtn.getSelection()) {
			adding = true;
			logger.info("Sync event invoked");
			eventBroker.send(PersonEventConstants.TOPIC_PERSON_NEW, p);
			logger.info("Sync event complete");
		}
		if (addAsyncEvtBtn.getSelection()) {
			adding = true;
			logger.info("Async event invoked");
			eventBroker.post(PersonEventConstants.TOPIC_PERSON_NEW, p);
			logger.info("Async event complete");
		}
		if (addCtxBtn.getSelection()) {
			adding = true;
			application.getContext().set(Person.class, p);
			logger.info("Object added to context");
		}

		if (deleteAsyncEvtBtn.getSelection() || deleteSyncEvtBtn.getSelection()
				|| deleteCtxBtn.getSelection()) {
			if (adding) {
				logger.info("Object cannot be added and deleted at once");
			} else {
				if (deleteAsyncEvtBtn.getSelection()) {
					eventBroker.post(PersonEventConstants.TOPIC_PERSON_DELETE,
							p);
				}
				if (deleteSyncEvtBtn.getSelection()) {
					eventBroker.send(PersonEventConstants.TOPIC_PERSON_DELETE,
							p);
				}
				if (deleteCtxBtn.getSelection()) {
					application.getContext().remove(Person.class);
				}
			}
		}

	}

	private Person createPersonFromForm() {
		Person p = new Person();
		p.setEmail(emailInput.getText());
		p.setFirstName(firstNameInput.getText());
		p.setLastName(lastNameInput.getText());
		return p;
	}

	@Focus
	public void setFocus() {
		firstNameInput.setFocus();
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