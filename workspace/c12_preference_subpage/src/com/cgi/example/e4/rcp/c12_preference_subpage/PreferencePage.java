package com.cgi.example.e4.rcp.c12_preference_subpage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Empty preference page.
 * 
 * @author Tomáš Vejpustek
 *
 */
public class PreferencePage extends org.eclipse.jface.preference.PreferencePage {

	public PreferencePage() {
		super("Preference Subpage");
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}
	
}
