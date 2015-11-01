package persistence.dao;

import java.util.List;

public interface GenericDAO<T>
{
	Long countAll();

	T create(T t);

	void delete(T t);

	T update(T t);

	List<T> findAll();
}