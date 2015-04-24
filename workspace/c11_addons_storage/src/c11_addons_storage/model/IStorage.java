package c11_addons_storage.model;

public interface IStorage<T> {

	void insert(T object);

	void update(T object);

	void delete(T object);
}
