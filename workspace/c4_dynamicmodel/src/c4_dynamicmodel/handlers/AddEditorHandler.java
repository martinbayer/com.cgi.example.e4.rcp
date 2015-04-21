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
package c4_dynamicmodel.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

public class AddEditorHandler {

	@Execute
	public void execute(Shell shell, MApplication application,
			EPartService partService, EModelService modelService) {

		MPart part = MBasicFactory.INSTANCE.createPart();
		part.setElementId("mynewid");
		part.setLabel("Created on: "
				+ LocalDateTime.now().format(
						DateTimeFormatter.ofPattern("HH:mm:ss")));
		part.setContributionURI("bundleclass://c4_dynamicmodel/c4_dynamicmodel.parts.SamplePart");
		MPartStack stack = (MPartStack) modelService.find("allwindowsstack",
				application);
		stack.getChildren().add(part);
		partService.activate(part);
	}
}
