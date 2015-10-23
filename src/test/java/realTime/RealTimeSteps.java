package realTime;

import integration.tools.EntityManagerHandler;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import model.Stand;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import persistence.dao.StandDAO;
import persistence.dao.StandDAOImpl;

public class RealTimeSteps
{
	private StandDAO standDAO;

	private static final Logger LOGGER = Logger.getLogger(RealTimeSteps.class.getCanonicalName());

	@Given("the bike stands have random capacity and occupancy")
	@Pending
	public void givenTheBikeStandsHaveRandomCapacityAndOccupancy()
	{
		//Messin' with writing to DB
//		EntityManager entityManager = EntityManagerHandler.getInstance().getEntityManager();
//		entityManager.getTransaction().begin();
//		standDAO = new StandDAOImpl(entityManager);
//		Stand stand = new Stand();
//		stand.setCapacity(1);
//		stand.setOccupancy(1);
//		stand.setLatitude(50); 
//		stand.setLongitude(6);
//		stand.setName("Some Stand");
//		stand.setNumberInNetwork((int)(Math.random()*1000));
//		standDAO.create(stand);
//		entityManager.getTransaction().commit();
		LOGGER.info("Setting random capacity and occupancy on stands");
	}

	@Given("all the bike stands currently have a capacity of $capacity and an occupancy of $occupancy")
	@Pending
	public void givenAllTheBikeStandsCurrentlyHaveACapacityOfAndAnOccupancyOf(int capacity, int occupancy)
	{
		// PENDING
	}

	@When("the user navigates to the $screenName screen")
	@Pending
	public void whenTheUserNavigatesToScreen(String screenName)
	{
		LOGGER.info("I am looking at the ");
	}

	@When("the user orders the table by $column")
	@Pending
	public void whenTheUserOrdersTheTableBycolumn(String column)
	{
		// PENDING
	}

	@Then("$numberOfStands stands will be visible in a table")
	@Pending
	public void thenStandsWillBeVisibleInATable(int numberOfStands)
	{
		// PENDING
	}

	@Then("all stands will have a capacity of $capacity")
	@Pending
	public void thenAllStandsWillHaveACapacityOf(int capacity)
	{
		// PENDING
	}
	
	@Then("the table is ordered by $column")
	@Pending
	public void thenTheTableIsOrderedBycolumn(String columnName) {
	  // PENDING
	}

	@Then("all stands will have an occupancy of $occupancy")
	@Pending
	public void thenAllStandsWillHaveAnOccupancyOf(int occupancy)
	{
		// PENDING
	}

	@Then("all stands are highlighted as $highlightType")
	@Pending
	public void thenAllStandsAreHighlightedAsempty(String highlightType)
	{
		// PENDING
	}
}
