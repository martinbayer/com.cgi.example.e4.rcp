/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package c3_commands_handlers.handlers;

import java.nio.file.Paths;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import c3_commands_handlers.parts.TxtViewPart;

public class OpenHandler {

	@Execute
	public void execute(Shell shell, @Active MPart part) {
		FileDialog dialog = new FileDialog(shell);
		dialog.setFilterExtensions(new String[] { "*.txt" });
		String file = dialog.open();
		if (part.getObject() instanceof TxtViewPart) {
			((TxtViewPart) part.getObject()).setText(Paths.get(file));
		}
	}
}
