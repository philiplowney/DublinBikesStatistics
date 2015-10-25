package systemTest.stories.realTime;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import model.Stand;
import model.StandState;

import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import persistence.dao.StandDAO;
import persistence.dao.StandDAOImpl;
import systemTest.tools.EntityManagerHandler;
import systemTest.tools.StandDescriptionFetcher;

public class RealTimeSteps
{
	private StandDAO standDAO;
	private int totalQuantityOfStands = 0;

	private static final Logger LOGGER = Logger.getLogger(RealTimeSteps.class.getCanonicalName());
	private EntityManager entityManager;

	@BeforeStories
	public void setUpEachStory()
	{
		entityManager = EntityManagerHandler.getInstance().getEntityManager();
		standDAO = new StandDAOImpl(entityManager);
		entityManager.getTransaction().begin();
		// Delete existing stands
		List<Stand> allExtantStands = standDAO.findAll();
		for(Stand extantStandToDelete: allExtantStands)
		{
			standDAO.delete(extantStandToDelete.getId());
		}
		// Load the standard stand list from Json file
		List<Stand> stands = StandDescriptionFetcher.getInstance().getDescriptions();
		// Save the standard stand list
		for(Stand stand : stands)
		{
			standDAO.create(stand);
		}
		entityManager.getTransaction().commit();
		totalQuantityOfStands = stands.size();
		LOGGER.info("Stands saved in network - total quantity: "+totalQuantityOfStands);
	}

	@Given("the bike stands have random capacity and occupancy")
	public void givenTheBikeStandsHaveRandomCapacityAndOccupancy()
	{
		List<Stand> allStands = standDAO.findAll();
		entityManager.getTransaction().begin();
		for(Stand stand : allStands)
		{
			Random rand = new Random();
			int spaces = rand.nextInt(21);
			int bikes = rand.nextInt(21);
			stand.setState(new StandState(Date.from(Instant.now()), bikes, spaces));
			standDAO.update(stand);
		}
		entityManager.getTransaction().commit();
		LOGGER.info("Stands configured with random states");
	}

	@Given("all the bike stands currently have a capacity of $capacity and an occupancy of $occupancy")
	public void givenAllTheBikeStandsCurrentlyHaveACapacityOfAndAnOccupancyOf(int capacity, int occupancy)
	{
		List<Stand> allStands = standDAO.findAll();
		entityManager.getTransaction().begin();
		for(Stand stand : allStands)
		{
			stand.setState(new StandState(Date.from(Instant.now()), occupancy, capacity-occupancy));
			standDAO.update(stand);
		}
		entityManager.getTransaction().commit();
		LOGGER.info("Stands configured with capacity of "+capacity+" and occupancy of "+occupancy);
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
	public void thenTheTableIsOrderedBycolumn(String columnName)
	{
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
