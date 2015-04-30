package com.cgi.example.e4.rcp.c12_preference_core;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

/**
 * Loads and displays all available preference pages.
 * 
 * @author Tomáš Vejpustek
 *
 */
public class PreferenceHandler {

	@Execute
	public void execute(Shell shell, IExtensionRegistry registry, Logger logger, IEclipseContext context,
			@Optional @Named("org.eclipse.ui.window.preferences.pageid") String pageId /* command parameter */) {
		IConfigurationElement[] config = registry.getConfigurationElementsFor("c12_preference_core.preferencePage");
		
		Map<String, PreferenceNode> nodesById = new HashMap<>();
		Map<String, String> parentPageById = new HashMap<>();
		/* load preference extensions */
		for (IConfigurationElement elem : config) {
			String id = elem.getAttribute("id");
			try {
				/* load preference page from extension */
				IPreferencePage preference = (IPreferencePage) elem.createExecutableExtension("class");
				/* inject context */
				ContextInjectionFactory.inject(preference, context);
				/* create preference node */
				PreferenceNode node = new PreferenceNode(id, preference);
				nodesById.put(id, node);
				parentPageById.put(id, elem.getAttribute("parent"));
			} catch (CoreException ce) {
				logger.error(ce, "Cannot create preference page " + id + ".");
			}
		}
		
		PreferenceManager manager = new PreferenceManager();
		/* append preference nodes to manager or each other */
		for (Map.Entry<String, PreferenceNode> element : nodesById.entrySet()) {
			String parentId = parentPageById.get(element.getKey());
			if (parentId == null) {
				/* no parent page, append to root */
				manager.addToRoot(element.getValue());
			} else {
				/* parent page, append to it */
				PreferenceNode parentNode = nodesById.get(parentId);
				if (parentNode != null) {
					parentNode.add(element.getValue());
				} else {
					/* cannot find parent node, append to root */
					logger.warn("Cannot find parent page " + parentId + " for page " + element.getKey());
					manager.addToRoot(element.getValue());
				}
			}
		}
		
		/* create and show preference dialog */
		PreferenceDialog dialog = new PreferenceDialog(shell, manager);
		if (pageId != null) {
			/* set opened page id (if specified) */
			dialog.setSelectedNode(pageId);
		}
		dialog.open();
	}
}
