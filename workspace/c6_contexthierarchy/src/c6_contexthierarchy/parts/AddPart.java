package c6_contexthierarchy.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import c6_contexthierarchy.parts.logic.EditFieldDirtyListener;
import c6_contexthierarchy.parts.model.Person;

public class AddPart {

	private Label nameLabel;
	private Text nameInput;

	@Inject
	private MWindow window;

	@Inject
	@Optional
	private MPerspective perspective;

	@Inject
	private MPart part;

	@Inject
	private MApplication application;

	@Inject
	private MDirtyable dirty;

	@Inject
	private Shell shell;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		/* name */
		nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("Name:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		nameLabel.setLayoutData(gridData);

		nameInput = new Text(parent, SWT.BORDER);
		nameInput.addModifyListener(new EditFieldDirtyListener(dirty));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		nameInput.setLayoutData(gridData);

		createButtons(parent);

	}

	private void createButtons(Composite parent) {
		GridData gridData = new GridData();
		Button savePersonToWorkbenchBtn = new Button(parent, SWT.NONE);
		savePersonToWorkbenchBtn.setText("Save data to workbench");
		savePersonToWorkbenchBtn.addSelectionListener(new SavePerson(this,
				ContextSource.WORKBENCH));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		savePersonToWorkbenchBtn.setLayoutData(gridData);

		Button clearWorkbenchCtx = new Button(parent, SWT.NONE);
		clearWorkbenchCtx.setText("Clear workbench context");
		clearWorkbenchCtx.addSelectionListener(new ClearContext(this,
				ContextSource.WORKBENCH));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		clearWorkbenchCtx.setLayoutData(gridData);

		Button savePersonToWindowBtn = new Button(parent, SWT.NONE);
		savePersonToWindowBtn.setText("Save data to window");
		savePersonToWindowBtn.addSelectionListener(new SavePerson(this,
				ContextSource.WINDOW));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		savePersonToWindowBtn.setLayoutData(gridData);

		Button clearWindowCtx = new Button(parent, SWT.NONE);
		clearWindowCtx.setText("Clear window context");
		clearWindowCtx.addSelectionListener(new ClearContext(this,
				ContextSource.WINDOW));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		clearWindowCtx.setLayoutData(gridData);

		Button savePersonToPartBtn = new Button(parent, SWT.NONE);
		savePersonToPartBtn.setText("Save data to part");
		savePersonToPartBtn.addSelectionListener(new SavePerson(this,
				ContextSource.PART));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		savePersonToPartBtn.setLayoutData(gridData);

		Button clearPartCtx = new Button(parent, SWT.NONE);
		clearPartCtx.setText("Clear part context");
		clearPartCtx.addSelectionListener(new ClearContext(this,
				ContextSource.PART));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		clearPartCtx.setLayoutData(gridData);
	}

	@Focus
	public void setFocus() {
		nameInput.setFocus();
	}

	public void savePerson(ContextSource contextSource) {

		IEclipseContext ctx = null;
		switch (contextSource) {
		case PART:
			ctx = part.getContext();
			break;
		case WINDOW:
			ctx = window.getContext();
			break;
		case WORKBENCH:
			ctx = application.getContext();
			break;
		default:
			MessageDialog.openWarning(shell, "Invalid context",
					"Invalid context found:" + contextSource);
			return;

		}
		ctx.set(Person.class, createPersonFromForm());
		dirty.setDirty(false);
	}

	public void clearContext(ContextSource contextSource) {
		IEclipseContext ctx = null;
		switch (contextSource) {
		case PART:
			ctx = part.getContext();
			break;
		case WINDOW:
			ctx = window.getContext();
			break;
		case WORKBENCH:
			ctx = application.getContext();
			break;
		default:
			MessageDialog.openWarning(shell, "Invalid context",
					"Invalid context found:" + contextSource);
			return;

		}
		ctx.remove(Person.class);
		dirty.setDirty(false);
	}

	@Persist
	public void save() {
		dirty.setDirty(false);
	}

	private Person createPersonFromForm() {
		Person p = new Person();
		p.setName(nameInput.getText());
		return p;
	}

	class SavePerson extends SelectionAdapter {
		private AddPart part;
		private ContextSource contextSource;

		public SavePerson(AddPart part, ContextSource contextSource) {
			this.part = part;
			this.contextSource = contextSource;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			part.savePerson(this.contextSource);
		}
	}

	class ClearContext extends SelectionAdapter {
		private AddPart part;
		private ContextSource contextSource;

		public ClearContext(AddPart part, ContextSource contextSource) {
			this.part = part;
			this.contextSource = contextSource;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			part.clearContext(this.contextSource);
		}
	}
}
