package c10_fragments_mapycz.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class OpenMapyczMapHandler {
	@Execute
	public void execute(EModelService modelService, EPartService partService,
			MApplication application) {
		MPart part = MBasicFactory.INSTANCE.createPart();
		part.setElementId("googleMapPart");
		part.setLabel("Mapy.cz tab created on "
				+ LocalDateTime.now().format(
						DateTimeFormatter.ofPattern("HH:mm:ss")));
		part.setContributionURI("bundleclass://c10_fragments_mapycz/c10_fragments_mapycz.parts.MapyczMapBrowserPart");
		MPartStack stack = (MPartStack) modelService.find(
				"c10_fragments.partstack.maps", application);
		stack.getChildren().add(part);
		partService.activate(part);
	}

}