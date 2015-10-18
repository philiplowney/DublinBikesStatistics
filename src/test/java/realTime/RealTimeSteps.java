package realTime;

import java.util.logging.Logger;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class RealTimeSteps
{
	private static final Logger LOGGER = Logger.getLogger(RealTimeSteps.class.getCanonicalName());

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
