package persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class GenericDAOImpl<T> implements GenericDAO<T>
{
	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl()
	{
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public long countAll()
	{
		final StringBuffer queryString = new StringBuffer("SELECT count(o) from ");
		queryString.append(type.getSimpleName()).append(" o ");
		final Query query = this.em.createQuery(queryString.toString());
		return (Long) query.getSingleResult();
	}

	@Override
	public T create(final T t)
	{
		this.em.persist(t);
		return t;
	}

	@Override
	public void delete(final Object id)
	{
		this.em.remove(this.em.getReference(type, id));
	}

	@Override
	public T find(final Object id)
	{
		return (T) this.em.find(type, id);
	}

	@Override
	public T update(final T t)
	{
		return this.em.merge(t);
	}
}