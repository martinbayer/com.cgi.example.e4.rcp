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
package c6_contexthierarchy.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
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

import c6_contexthierarchy.parts.model.Person;

public class ShowDataFromContextPart {

	private Label firstNameLabel, lastNameLabel, emailLabel;
	private Text firstNameInput, lastNameInput, emailInput;

	@Inject
	private MPerspective perspective;

	@Inject
	private Shell shell;

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

		Button loadBtn = new Button(parent, SWT.NONE);
		loadBtn.setText("Load person");
		loadBtn.addSelectionListener(new DisplayCurrentPerson(this));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		loadBtn.setLayoutData(gridData);
	}

	@Focus
	public void setFocus() {
		firstNameInput.setFocus();
	}

	public void refreshData() {
		Person p = perspective.getContext().get(Person.class);
		if (p == null) {
			MessageDialog.openWarning(shell, "No Person for perspective",
					"No person for current perspective found");
			return;
		}
		firstNameInput.setText(p.getFirstName());
		lastNameInput.setText(p.getLastName());
		emailInput.setText(p.getEmail());
	}

	class DisplayCurrentPerson extends SelectionAdapter {
		private ShowDataFromContextPart part;

		public DisplayCurrentPerson(ShowDataFromContextPart part) {
			this.part = part;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			part.refreshData();
		}
	}
}
