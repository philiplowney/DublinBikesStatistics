package systemTest.stories.realTime;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import model.StandDescription;

import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import persistence.dao.StandDescriptionDAO;
import persistence.dao.StandDescriptionDAOImpl;
import systemTest.tools.EntityManagerHandler;
import systemTest.tools.StandDescriptionFetcher;

public class RealTimeSteps
{
	private StandDescriptionDAO standDAO;

	private static final Logger LOGGER = Logger.getLogger(RealTimeSteps.class.getCanonicalName());

	@BeforeStories
	public void setUpEachStory()
	{
		EntityManager entityManager = EntityManagerHandler.getInstance().getEntityManager();
		standDAO = new StandDescriptionDAOImpl(entityManager);
		entityManager.getTransaction().begin();
		// Delete existing stands
		List<StandDescription> allExtantStands = standDAO.findAll();
		for(StandDescription extantStandToDelete: allExtantStands)
		{
			standDAO.delete(extantStandToDelete.getId());
		}
		// Load the standard stand list from Json file
		List<StandDescription> stands = StandDescriptionFetcher.getInstance().getDescriptions();
		// Save the standard stand list
		for(StandDescription stand : stands)
		{
			standDAO.create(stand);
		}
		entityManager.getTransaction().commit();
	}

	@Given("the bike stands have random capacity and occupancy")
	public void givenTheBikeStandsHaveRandomCapacityAndOccupancy()
	{

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