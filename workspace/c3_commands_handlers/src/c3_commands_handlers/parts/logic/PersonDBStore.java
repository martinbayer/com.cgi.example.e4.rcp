package c3_commands_handlers.parts.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import c3_commands_handlers.parts.model.Person;

public enum PersonDBStore implements IDBStore<Person> {
	INSTANCE;

	private List<Person> persons = new ArrayList<>();

	@Override
	public boolean storeToDb(Person person) {
		return persons.add(person);
	}

	@Override
	public List<Person> getObjects() {
		return Collections.unmodifiableList(persons);
	}

}
