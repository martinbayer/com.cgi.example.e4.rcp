package c7_eventservice.parts.logic;

public interface PersonEventConstants {

	public static final String TOPIC_PERSON_GENERAL = "TOPIC_PERSON/*";
	public static final String TOPIC_PERSON_NEW = "TOPIC_PERSON/NEW";
	public static final String TOPIC_PERSON_DELETE = "TOPIC_PERSON/DELETED";
	public static final String TOPIC_PERSON_NAME_CHANGED = "TOPIC_PERSON/NAME_CHANGED";
	public static final String TOPIC_PERSON_EMAIL_CHANGED = "TOPIC_PERSON/EMAIL_CHANGED";
}
