package persistence.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Stand;

@Stateless
public class StandDAOImpl extends GenericDAOImpl<Stand> implements StandDAO
{
	public StandDAOImpl()
	{
		super();
	}

	public StandDAOImpl(EntityManager em)
	{
		super(em);
		System.out.println("A StandDAOImpl has been created");
	}

	@Override
	public Stand findByNumber(int standNumber)
	{
		TypedQuery<Stand> query = em.createNamedQuery(Stand.NAMED_QUERY_FIND_BY_NUMBER, Stand.class);
		query.setParameter(Stand.QUERY_PARAMETER_STAND_NUMBER, standNumber);
		return query.getSingleResult();
	}

	@Override
	public boolean doesStandExistWithNumber(int number)
	{
		TypedQuery<Long> query = em.createNamedQuery(Stand.NAMED_QUERY_COUNT_WITH_NUMBER, Long.class);
		query.setParameter(Stand.QUERY_PARAMETER_STAND_NUMBER, number);
		return query.getSingleResult()>0;
	}

	@Override
	public List<Stand> findAllCurrentStands()
	{
		TypedQuery<Stand> query = em.createNamedQuery(Stand.FIND_ALL_CURRENT, Stand.class);
		return query.getResultList();
	}

	@Override
	public int findTotalBikesCurrentlyAvailable()
	{
		TypedQuery<Long> query = em.createNamedQuery(Stand.COUNT_CURRENTLY_AVAILABLE_BIKES, Long.class);
		return new Long(query.getSingleResult()).intValue();
	}
}
