package c7_eventservice.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import c7_eventservice.parts.logic.PersonEventConstants;
import c7_eventservice.parts.model.Person;

public class AppStatusBar {

	private Label statusLabel;

	@PostConstruct
	public void createGui(Composite parent) {
		Composite statusBar = new Composite(parent, SWT.NONE);
		statusBar.setLayout(new GridLayout());

		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.minimumWidth = 300;
		statusLabel = new Label(statusBar, SWT.NONE);
		statusLabel.setLayoutData(layoutData);
		statusLabel.setForeground(Display.getCurrent().getSystemColor(
				SWT.COLOR_RED));
	}

	@Inject
	@Optional
	public void personNameChangedEvent(
			@UIEventTopic(value = PersonEventConstants.TOPIC_PERSON_NAME_CHANGED) Person p) {
		System.out.println("Person's name changed");
		statusLabel.setText("Person's name changed to '" + p.getName() + "'");
	}

	@Inject
	@Optional
	public void personEmailChangedEvent(
			@UIEventTopic(value = PersonEventConstants.TOPIC_PERSON_EMAIL_CHANGED) Person p) {
		System.out.println("Person's email changed");
		statusLabel.setText("Person's email changed to '" + p.getEmail() + "'");
	}
}