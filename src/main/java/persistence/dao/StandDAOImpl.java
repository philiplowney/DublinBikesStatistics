package persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

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
}
