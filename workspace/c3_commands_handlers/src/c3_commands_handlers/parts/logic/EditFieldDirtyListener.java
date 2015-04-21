package c3_commands_handlers.parts.logic;

import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class EditFieldDirtyListener implements ModifyListener{
	private MDirtyable dirty;
	public EditFieldDirtyListener(MDirtyable dirty){
		this.dirty = dirty;
	}
	@Override
	public void modifyText(ModifyEvent e) {
		dirty.setDirty(true);
	}
};