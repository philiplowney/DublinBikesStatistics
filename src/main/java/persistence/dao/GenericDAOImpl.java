package persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class GenericDAOImpl<T> implements GenericDAO<T>
{
	@PersistenceContext(unitName="DublinBikesAnalytics")
	protected EntityManager em;

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl()
	{
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	public GenericDAOImpl(EntityManager entityManager)
	{
		this();
		this.em = entityManager;
	}

	@Override
	public Long countAll()
	{
		final StringBuffer queryString = new StringBuffer("SELECT count(o) from ");
		queryString.append(type.getSimpleName()).append(" o ");
		final TypedQuery<Long> query = this.em.createQuery(queryString.toString(), Long.class);
		return query.getSingleResult();
	}
	
	@Override
	public List<T> findAll()
	{
		final StringBuffer queryString = new StringBuffer("SELECT o from ");
		queryString.append(type.getSimpleName()).append(" o ");
		final TypedQuery<T> query = this.em.createQuery(queryString.toString(), type);
		return query.getResultList();
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