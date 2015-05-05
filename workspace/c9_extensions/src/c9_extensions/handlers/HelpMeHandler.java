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
package c9_extensions.handlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.di.annotations.Execute;

import c9_extensions_plugin.IHelpProvider;

public class HelpMeHandler {
	//nejdrive vysvetlit v pluginu extension point + potom pluginy s implementaci a 'pripojeni' na extension point
	private static final String HELPER_EXT_POINT_ID = "helper.id";

	@Execute
	public void execute(IExtensionRegistry registry) {
		callForHelp(registry);
	}

	private void callForHelp(IExtensionRegistry registry) {
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(HELPER_EXT_POINT_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IHelpProvider) {
					executeHelper((IHelpProvider) o);
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void executeHelper(IHelpProvider o) {
		ISafeRunnable runnable = new ISafeRunnable() {

			@Override
			public void run() throws Exception {
				o.provideHelp();
			}

			@Override
			public void handleException(Throwable exception) {
				System.out.println("Exception in client");
				exception.printStackTrace();
			}
		};
		SafeRunner.run(runnable);
	}
}
