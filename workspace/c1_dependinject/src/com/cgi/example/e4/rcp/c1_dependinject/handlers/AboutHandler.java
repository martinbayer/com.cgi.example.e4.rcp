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
package com.cgi.example.e4.rcp.c1_dependinject.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.cgi.example.e4.rcp.c1_dependinject.parts.DependencyInjection;

public class AboutHandler {
	@Execute
	public void execute(Shell shell,
			@Optional @Named(value = DependencyInjection.DI_AUTHOR_ID) String authorName) {
		MessageDialog.openInformation(shell, "Author of the input",
				"Author's name: "
						+ (authorName == null ? "Unknown" : authorName));
	}
}
