package com.cgi.example.e4.rcp.c12_preference_page;

/**
 * Event topic for preference.
 * 
 * @author Tomáš Vejpustek
 *
 */
public interface PreferenceTopic {
	/** Event constant for topic -- do not use */
	static final String TOPIC = "TOPIC_C12_PREFERENCE_PAGE";
	/** Preference has changed */
	static final String CHANGE = TOPIC + "/CHANGE";
}
