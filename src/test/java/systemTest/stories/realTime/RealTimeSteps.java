package systemTest.stories.realTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import model.Stand;
import model.StandState;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.embedder.Embedder;
import org.junit.Assert;

import systemTest.tools.StandDescriptionFetcher;
import systemTest.tools.WebServiceHandler;
import systemTest.tools.pageObjects.IndexPage;
import systemTest.tools.pageObjects.TableViewPage;
import systemTest.tools.selenium.WebDriverManager;
import systemTest.ws.WebServiceTest;

public class RealTimeSteps extends Embedder
{
	private WebServiceHandler wsHandler = new WebServiceHandler(WebServiceTest.BASE_REST_ADDRESS);
	private int totalQuantityOfTestStands = 0;

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
		List<Stand> subList = new ArrayList<>();
		for(int i=0; i<stands.size(); i+=10)
		{
			subList.add(stands.get(i));
		}
		// Save a small, test-version of the list of stands
		for(Stand stand : subList)
		{
			wsHandler.callCreateStand(stand);
		}
		totalQuantityOfTestStands = subList.size();
		LOGGER.info("Stands saved in network - total quantity: "+totalQuantityOfTestStands);
		new IndexPage().navToIndex();
		LOGGER.info("Navigated to home page");
	}
	
	@AfterStories
	public void tidyUpAfterStories()
	{
		WebDriverManager.getInstance().shutDown();
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
	public void whenTheUserNavigatesToScreen(String screenName) throws InterruptedException
	{
		tableViewPage = new IndexPage().getLeftMenu().navRealTimeTableView();
		Thread.sleep(500);
		LOGGER.info("I am looking at the table view page");
	}

	@When("the user orders the table by $column")
	public void whenTheUserOrdersTheTableBycolumn(String column) throws InterruptedException
	{
		tableViewPage.sortTableByColumn(column);
		Thread.sleep(500);
	}

	@Then("all stands will be visible in a table")
	public void thenAllStandsWillBeVisibleInATable()
	{
		int rows = tableViewPage.countRowsInTable();
		Assert.assertEquals(totalQuantityOfTestStands, rows);
	}

	@Then("all stands will have a capacity of $capacity")
	public void thenAllStandsWillHaveACapacityOf(int capacity)
	{
		Map<String, StandState> tableRows = tableViewPage.getDisplayedStandStates();
		for(StandState row : tableRows.values())
		{
			Assert.assertEquals(capacity, row.getBikesAvailable() + row.getPlacesAvailable());
		}
	}

	@Then("the table is ordered by $column")
	public void thenTheTableIsOrderedBycolumn(String columnName)
	{
		Map<String, StandState> tableRows = tableViewPage.getDisplayedStandStates();
		List<String> valuesInStandColumn = new ArrayList<>();
		List<Integer> valuesInBikesColumn = new ArrayList<>();
		List<Integer> valuesInSpacesColumn = new ArrayList<>();
		
		Iterator<Map.Entry<String, StandState>> it = tableRows.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, StandState> row = it.next();
			valuesInStandColumn.add(row.getKey());
			valuesInBikesColumn.add(row.getValue().getBikesAvailable());
			valuesInSpacesColumn.add(row.getValue().getPlacesAvailable());
		}
		
		if(columnName.equals(TableViewPage.HEADING_BIKES) || columnName.equals(TableViewPage.HEADING_SPACES))
		{
			List<Integer> vals = columnName.equals(TableViewPage.HEADING_BIKES) ? valuesInBikesColumn : valuesInSpacesColumn; 
			int lastValue = 0;
			for(int value : vals)
			{
				Assert.assertTrue("The column is in the wrong order", value >= lastValue);
				lastValue = value;
			}
		}
		else if(columnName.equals(TableViewPage.HEADING_STAND))
		{
			Character lastChar = null; 
			for(String stand : valuesInStandColumn)
			{
				if(lastChar!=null)
				{
					Assert.assertTrue("The column is in the wrong order: "+stand+" appearing after "+lastChar, ((Character)stand.charAt(0)).compareTo(lastChar) >= 0);
				}
				lastChar = stand.charAt(0);
			}
		}
		else
		{
			Assert.fail("Column name not recognised: "+columnName);
		}
	}

	@Then("all stands will have an occupancy of $occupancy")
	public void thenAllStandsWillHaveAnOccupancyOf(int occupancy)
	{
		Map<String, StandState> tableRows = tableViewPage.getDisplayedStandStates();
		for(StandState row : tableRows.values())
		{
			Assert.assertEquals(occupancy, row.getBikesAvailable());
		}
	}

	@Then("all stands are highlighted as $highlightType")
	@Pending
	public void thenAllStandsAreHighlightedAsempty(String highlightType)
	{
		// PENDING
	}
}
