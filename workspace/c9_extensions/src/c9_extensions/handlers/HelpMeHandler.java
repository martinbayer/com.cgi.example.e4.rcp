package c9_extensions.handlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.di.annotations.Execute;

import c9_extensions_plugin.IHelpProvider;

public class HelpMeHandler {
	private static final String HELPER_EXT_POINT_ID = "helper.id";

	@Execute
	public void execute(IExtensionRegistry registry) {
		callForHelp(registry);
	}

	private void callForHelp(IExtensionRegistry registry) {
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(HELPER_EXT_POINT_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IHelpProvider) {
					executeHelper((IHelpProvider) o);
				}
			}
		} catch (CoreException ex) {
			System.out.println("Exception occured in extension");
		}
	}

	private void executeHelper(IHelpProvider o) {
		ISafeRunnable runnable = new ISafeRunnable() {

			@Override
			public void run() throws Exception {
				o.provideHelp();
			}

			@Override
			public void handleException(Throwable exception) {
				System.out.println("Exception in client");
			}
		};
		SafeRunner.run(runnable);
	}
}
