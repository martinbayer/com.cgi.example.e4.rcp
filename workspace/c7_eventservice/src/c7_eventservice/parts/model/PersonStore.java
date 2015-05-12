package c7_eventservice.parts.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum PersonStore {
	INSTANCE;

	private Set<Person> persons = new HashSet<>();

	public boolean addPerson(Person p) {
		return persons.add(p);
	}

	public boolean removePerson(Person p) {
		return persons.remove(p);
	}

	public Set<Person> getPersons() {
		return Collections.unmodifiableSet(persons);
	}
}
