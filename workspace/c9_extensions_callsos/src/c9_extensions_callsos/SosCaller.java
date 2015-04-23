package c9_extensions_callsos;

import c9_extensions_plugin.IHelpProvider;

public class SosCaller implements IHelpProvider {

	public SosCaller() {
	}

	@Override
	public void provideHelp() {
		System.out.println("Calling SOS");
	}

}
