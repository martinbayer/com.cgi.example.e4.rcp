package com.cgi.example.e4.rcp.c12_preference_core;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

/**
 * Exits application.
 * 
 * @author Tomáš Vejpustek
 *
 */
public class ExitHandler {

	@Execute
	public void exit(IWorkbench workbench) {
		workbench.close();
	}
}
