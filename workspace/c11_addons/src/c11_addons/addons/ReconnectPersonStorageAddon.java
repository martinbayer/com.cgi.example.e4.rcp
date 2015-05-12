package c11_addons.addons;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.osgi.service.event.Event;

import c11_addons.AddonsEvents;
import c11_addons_storage_DB.PersonDBStorage;

public class ReconnectPersonStorageAddon {

	@Inject
	@Optional
	public void storageReconect(
			@UIEventTopic(AddonsEvents.RECONNECT_PERSON_STORAGE_TOPIC) Event event,
			IEventBroker eventBroker, @Optional PersonDBStorage personStorage) {
		if (personStorage == null) {
			System.out.println("no storage found in the context");
			return;
		}
		if (personStorage.reconnect()) {
			eventBroker.post(AddonsEvents.PERSON_STORAGE_RECONNECT_SUCCESFULL,
					null);
		} else {
			eventBroker
					.post(AddonsEvents.PERSON_STORAGE_RECONNECT_FAILED, null);
		}
	}
}
