package persistence.dao;

import model.Stand;

public interface StandDAO extends GenericDAO<Stand>
{

	Stand findByNumber(int standNumber);

	boolean doesStandExistWithNumber(int number);

}
