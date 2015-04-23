package c9_extensions_sendmessage;

import c9_extensions_plugin.IHelpProvider;

public class SendHelpMessage implements IHelpProvider {

	public SendHelpMessage() {
	}

	@Override
	public void provideHelp() throws Exception {
		if (getRandomBoolean()) {
			System.out.println("Sending SMS message");
		} else {
			throw new Exception("Exception occured during sending SMS message");
		}
	}

	private boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}

}
