package c10_fragments_googlemaps.parts;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.osgi.framework.Bundle;

public class GoogleMapBrowserPart {

	private Browser browser;
	private List list;
	private URL fileURL;

	private File htmlFile;

	@PostConstruct
	public void createControls(Composite parent) {
		Bundle bundle = Platform.getBundle("c10_fragments_googlemaps");
		fileURL = bundle.getEntry("resources/map.html");
		try {
			htmlFile = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		parent.setLayout(new FillLayout());
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);

		try {
			browser = new Browser(sash, SWT.NONE);
			browser.addControlListener(new ControlListener() {

				@Override
				public void controlResized(ControlEvent e) {
					browser.execute("document.getElementById('map_canvas').style.width= "
							+ (browser.getSize().x - 20) + ";");
					browser.execute("document.getElementById('map_canvas').style.height= "
							+ (browser.getSize().y - 20) + ";");
				}

				@Override
				public void controlMoved(ControlEvent e) {
				}
			});
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: "
					+ e.getMessage());
			return;
		}

		Composite c = new Composite(sash, SWT.BORDER);
		c.setLayout(new GridLayout(1, true));
		Button b = new Button(c, SWT.PUSH);
		list = new List(c, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		b.setText("Where Am I ?");
		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double lat = ((Double) browser
						.evaluate("return map.getCenter().lat();"))
						.doubleValue();
				double lng = ((Double) browser
						.evaluate("return map.getCenter().lng();"))
						.doubleValue();
				list.add(lat + " : " + lng);
			}
		});

		if (fileURL != null) {
			browser.setUrl(htmlFile.toURI().toString());
			sash.setWeights(new int[] { 4, 1 });
		}

	}
}
