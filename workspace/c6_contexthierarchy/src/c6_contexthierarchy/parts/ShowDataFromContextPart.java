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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
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

	private Label nameLabel;
	private Text nameInput;

	@Inject
	private MWindow window;

	@Inject
	private MPart part;

	@Inject
	private Shell shell;

	@Inject
	private MApplication application;

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
		nameInput.setEditable(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		nameInput.setLayoutData(gridData);

		Button loadDataFromWorkbenchBtn = new Button(parent, SWT.NONE);
		loadDataFromWorkbenchBtn.setText("Load data from workbench");
		loadDataFromWorkbenchBtn.addSelectionListener(new DisplayPerson(this,
				ContextSource.WORKBENCH));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		loadDataFromWorkbenchBtn.setLayoutData(gridData);

		Button loadDataFromWindowBtn = new Button(parent, SWT.NONE);
		loadDataFromWindowBtn.setText("Load data from window");
		loadDataFromWindowBtn.addSelectionListener(new DisplayPerson(this,
				ContextSource.WINDOW));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		loadDataFromWindowBtn.setLayoutData(gridData);

		Button loadDataFromPartBtn = new Button(parent, SWT.NONE);
		loadDataFromPartBtn.setText("Load data from part");
		loadDataFromPartBtn.addSelectionListener(new DisplayPerson(this,
				ContextSource.PART));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		loadDataFromPartBtn.setLayoutData(gridData);
	}

	@Focus
	public void setFocus() {
		nameInput.setFocus();
	}

	public void refreshData(ContextSource contextSource) {

		IEclipseContext ctx = null;
		switch (contextSource) {
		case PART:
			ctx = part.getContext();
			break;
		case WINDOW:
			ctx = window.getContext();
			break;
		case WORKBENCH:
			ctx = application.getContext();
			break;
		default:
			MessageDialog.openWarning(shell, "Invalid context",
					"Invalid context found:" + contextSource);
			return;

		}
		Person p = ctx.get(Person.class);
		if (p == null) {
			MessageDialog.openWarning(shell, "No Person for perspective",
					"No person for current perspective found");
			return;
		}
		nameInput.setText(p.getName());
	}

	class DisplayPerson extends SelectionAdapter {
		private ShowDataFromContextPart part;
		private ContextSource contextSource;

		public DisplayPerson(ShowDataFromContextPart part,
				ContextSource contextSource) {
			this.part = part;
			this.contextSource = contextSource;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			part.refreshData(this.contextSource);
		}
	}
}
