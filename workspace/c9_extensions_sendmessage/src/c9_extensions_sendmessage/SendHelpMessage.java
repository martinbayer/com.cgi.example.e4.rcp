package c9_extensions_sendmessage;

import c9_extensions_plugin.IHelpProvider;

public class SendHelpMessage implements IHelpProvider {
	public SendHelpMessage() {
	}

	@Override
	public void provideHelp() throws Exception {
		System.out.println("Sending SMS message");

	}
}
