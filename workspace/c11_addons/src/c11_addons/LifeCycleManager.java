package c11_addons;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class LifeCycleManager {

	@PostContextCreate
	void postContextCreate(final IEventBroker eventBroker,
			IApplicationContext context) {
		// register for startup completed event and close the shell
		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE,
				new EventHandler() {
					@Override
					public void handleEvent(Event event) {
						System.out.println("starting");
					}
				});
		eventBroker.subscribe(UIEvents.UILifeCycle.APP_SHUTDOWN_STARTED,
				new EventHandler() {
					@Override
					public void handleEvent(Event event) {
						System.out.println("closing");
					}
				});
	}
}
