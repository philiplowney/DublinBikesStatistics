package persistence.dao;

public interface GenericDAO<T>
{
	long countAll();

	T create(T t);

	void delete(Object id);

	T find(Object id);

	T update(T t);
}