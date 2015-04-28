package com.cgi.example.e4.rcp.c12_preference_page;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.UIEventTopic;
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
	
	@Inject
	@Optional
	public void preferenceChanged(@UIEventTopic(PreferenceTopic.CHANGE) Object something,
			@Preference(value = "text") String text) {
		if (label != null && !label.isDisposed()) {
			label.setText(text);
		}
	}
}
