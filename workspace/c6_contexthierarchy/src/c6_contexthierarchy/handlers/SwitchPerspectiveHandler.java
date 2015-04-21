package c6_contexthierarchy.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SwitchPerspectiveHandler {
	private static final String FIRST_PERSPECTIVE_ID = "id.firstPerspective";
	private static final String SECOND_PERSPECTIVE_ID = "id.secondPerspective";

	@Execute
	public void execute(MApplication app, MWindow window,
			EPartService partService, EModelService modelService) {
		MPerspective activePerspective = modelService
				.getActivePerspective(window);

		String perspectiveToFindId = null;
		switch (activePerspective.getElementId()) {
		case FIRST_PERSPECTIVE_ID: {
			perspectiveToFindId = SECOND_PERSPECTIVE_ID;
			break;
		}
		case SECOND_PERSPECTIVE_ID: {
			perspectiveToFindId = FIRST_PERSPECTIVE_ID;
			break;
		}
		}
		if (perspectiveToFindId != null) {
			MPerspective element = (MPerspective) modelService.find(
					perspectiveToFindId, app);
			if (element != null) {
				partService.switchPerspective(element);
			}
		}
	}
}