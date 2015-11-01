package systemTest.stories.realTime;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import model.Stand;

import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import systemTest.tools.StandDescriptionFetcher;
import systemTest.tools.WebServiceHandler;
import systemTest.tools.pageObjects.IndexPage;
import systemTest.tools.pageObjects.TableViewPage;
import systemTest.ws.WebServiceTest;

public class RealTimeSteps
{
	private WebServiceHandler wsHandler = new WebServiceHandler(WebServiceTest.BASE_REST_ADDRESS);
	private int totalQuantityOfStands = 0;

	private static final Logger LOGGER = Logger.getLogger(RealTimeSteps.class.getCanonicalName());

	private TableViewPage tableViewPage;

	@BeforeStories
	public void setUpEachStory()
	{
		// Delete existing stands
		List<Stand> allExtantStands = wsHandler.callListStands();
		if(!allExtantStands.isEmpty())
		{
			for(Stand extantStandToDelete: allExtantStands)
			{
				wsHandler.callDeleteStand(extantStandToDelete.getNumber());
			}
		}
		// Load the standard stand list from Json file
		List<Stand> stands = StandDescriptionFetcher.getInstance().getDescriptions();
		// Save the standard stand list
		for(Stand stand : stands)
		{
			wsHandler.callCreateStand(stand);
		}
		totalQuantityOfStands = stands.size();
		LOGGER.info("Stands saved in network - total quantity: "+totalQuantityOfStands);
		
	}

	@Given("the bike stands have random capacity and occupancy")
	public void givenTheBikeStandsHaveRandomCapacityAndOccupancy()
	{
		List<Stand> allStands = wsHandler.callListStands();
		Random rand = new Random();
		for(Stand stand : allStands)
		{
			int spaces = rand.nextInt(21);
			int bikes = rand.nextInt(21);
			wsHandler.callUpdateStand(stand.getNumber(), bikes, spaces);
		}
		LOGGER.info("Stands configured with random states");
	}

	@Given("all the bike stands currently have a capacity of $capacity and an occupancy of $occupancy")
	public void givenAllTheBikeStandsCurrentlyHaveACapacityOfAndAnOccupancyOf(int capacity, int occupancy)
	{
		List<Stand> allStands = wsHandler.callListStands();
		for(Stand stand : allStands)
		{
			wsHandler.callUpdateStand(stand.getNumber(), occupancy, capacity-occupancy);
		}
		LOGGER.info("Stands configured with capacity of "+capacity+" and occupancy of "+occupancy);
	}

	@When("the user navigates to the $screenName screen")
	public void whenTheUserNavigatesToScreen(String screenName)
	{
		IndexPage indexPage = new IndexPage();
		indexPage.navToIndex();
		tableViewPage = indexPage.getLeftMenu().navRealTimeTableView();
		LOGGER.info("I am looking at the table view page");
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
