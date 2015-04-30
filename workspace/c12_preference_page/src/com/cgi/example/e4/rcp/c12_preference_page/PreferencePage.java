package com.cgi.example.e4.rcp.c12_preference_page;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Configures label on "Hello" part.
 * 
 * @author Tomáš Vejpustek
 */
public class PreferencePage extends org.eclipse.jface.preference.PreferencePage {
	@Inject
	@Preference
	private IEclipsePreferences preference;
	@Inject
	@Preference(value = "text")
	private String text;
	@Inject
	private Logger logger;
	@Inject
	private IEventBroker broker;
	
	private Text input;

	public PreferencePage() {
		super("Hello preference");
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Specify label:");
		input = new Text(container, SWT.BORDER);
		input.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		input.setText(text);
		return container;
	}
	
	@Override
	public boolean performOk() {
		if (input != null && !input.isDisposed()) {
			preference.put("text", input.getText());
			try {
				preference.flush();
			} catch (BackingStoreException bse) {
				logger.warn("Cannot store Hello preferences.");
			}
			broker.post(PreferenceTopic.CHANGE, text);
		}
		return true;
	}

}
