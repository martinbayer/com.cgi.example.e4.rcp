package c3_commands_handlers.parts.logic;

import java.util.List;

public interface IDBStore<T> {

	boolean storeToDb(T object);

	List<T> getObjects();
}
