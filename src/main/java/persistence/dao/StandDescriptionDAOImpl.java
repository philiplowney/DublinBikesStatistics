package persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import model.StandDescription;

@Stateless
public class StandDescriptionDAOImpl extends GenericDAOImpl<StandDescription> implements StandDescriptionDAO
{
	public StandDescriptionDAOImpl()
	{
		super();
	}
	public StandDescriptionDAOImpl(EntityManager em)
	{
		super(em);
		System.out.println("A StandDAOImpl has been created");
	}
}
