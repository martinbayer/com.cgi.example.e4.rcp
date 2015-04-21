package c3_commands_handlers.parts.addon;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

import c3_commands_handlers.parts.logic.PersonDBStore;

public class PersonDBStoreAddon {

	@Inject
	@Optional
	public void applicationStarted(
			@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event,
			IEclipseContext ctx) {
		// TODO Modify the UIEvents.UILifeCycle.APP_STARTUP_COMPLETE EventTopic
		// to a certain event you want to listen to.
		ctx.set(PersonDBStore.class, PersonDBStore.INSTANCE);
	}

}
