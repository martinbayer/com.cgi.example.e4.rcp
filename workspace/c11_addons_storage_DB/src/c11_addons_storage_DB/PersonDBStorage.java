package c11_addons_storage_DB;

import c11_addons_storage.model.IStorage;
import c11_addons_storage.model.Person;

public class PersonDBStorage implements IStorage<Person> {

	private static PersonDBStorage INSTANCE;

	public static final PersonDBStorage getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PersonDBStorage();
		}
		return INSTANCE;
	}

	@Override
	public void insert(Person person) {
		System.out.println("Person inserted " + person);
	}

	@Override
	public void update(Person person) {
		System.out.println("Person updated " + person);
	}

	@Override
	public void delete(Person person) {
		System.out.println("Person deleted " + person);
	}

	public boolean reconnect() {
		if (Math.random() < 0.5) {
			return true;
		} else {
			return false;
		}
	}

}
