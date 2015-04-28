package com.cgi.example.e4.rcp.c12_preference_page;


import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Part {
	private Label label;

	@PostConstruct
	public void createComponents(Composite parent,
			@Preference(value = "text") String text) {
		label = new Label(parent, SWT.NONE);
		label.setText(text);
	}
}
