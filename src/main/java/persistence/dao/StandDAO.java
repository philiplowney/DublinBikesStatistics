package persistence.dao;

import java.util.List;

import model.Stand;

public interface StandDAO extends GenericDAO<Stand>
{

	Stand findByNumber(int standNumber);

	boolean doesStandExistWithNumber(int number);

	List<Stand> findAllCurrentStands();

	int findTotalBikesCurrentlyAvailable();

}
