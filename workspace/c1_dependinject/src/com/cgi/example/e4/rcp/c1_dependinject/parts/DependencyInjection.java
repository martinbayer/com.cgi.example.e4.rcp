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
package com.cgi.example.e4.rcp.c1_dependinject.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DependencyInjection {
	public static final String DI_AUTHOR_ID = "author";
	private Text authorInput;
	private Label authorLabel;
	@Inject
	private MDirtyable dirty;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		authorLabel = new Label(parent, SWT.NONE);
		authorLabel.setText("Author: ");
		authorInput = new Text(parent, SWT.BORDER);
		authorInput.setMessage("Enter text to mark part as dirty");
		authorInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty.setDirty(true);
			}
		});
		authorInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	}

	@Focus
	public void setFocus() {
		authorInput.setFocus();
	}

	@Persist
	public void save(MApplication application) {
		IEclipseContext context = application.getContext();
		context.set(DI_AUTHOR_ID, authorInput.getText());
		dirty.setDirty(false);
	}
}