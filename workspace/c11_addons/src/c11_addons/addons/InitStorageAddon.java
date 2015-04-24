package c11_addons.addons;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;

import c11_addons_storage_DB.PersonDBStorage;

public class InitStorageAddon {

	@Inject
	@Optional
	public void applicationStarted(MApplication application) {
		application.getContext().set(PersonDBStorage.class,
				PersonDBStorage.getInstance());
	}

}
