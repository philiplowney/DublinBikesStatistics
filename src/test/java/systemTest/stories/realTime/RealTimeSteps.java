package systemTest.stories.realTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import model.Stand;
import model.StandState;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.embedder.Embedder;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import systemTest.tools.SystemTestHarness;
import systemTest.tools.pageObjects.IndexPage;
import systemTest.tools.pageObjects.TableViewPage;

public class RealTimeSteps extends Embedder
{
	private static final int NUMBER_OF_TEST_STANDS_CONFIGURED = 20;
	public static final Logger LOGGER = Logger.getLogger(RealTimeSteps.class.getCanonicalName());

	private TableViewPage tableViewPage;
	private SystemTestHarness testHarness = new SystemTestHarness();

	@Given("the bike stands have random capacity and occupancy")
	public void givenTheBikeStandsHaveRandomCapacityAndOccupancy()
	{
		List<Stand> allStands = testHarness.getWebServiceUpdater().callListStands();
		Random rand = new Random();
		for(Stand stand : allStands)
		{
			int spaces = rand.nextInt(21);
			int bikes = rand.nextInt(21);
			testHarness.getWebServiceUpdater().callUpdateStand(stand.getNumber(), bikes, spaces);
		}
		LOGGER.info("Stands configured with random states");
	}

	@Given("all the bike stands currently have a capacity of $capacity and an occupancy of $occupancy")
	public void givenAllTheBikeStandsCurrentlyHaveACapacityOfAndAnOccupancyOf(int capacity, int occupancy)
	{
		List<Stand> allStands = testHarness.getWebServiceUpdater().callListStands();
		for(Stand stand : allStands)
		{
			testHarness.getWebServiceUpdater().callUpdateStand(stand.getNumber(), occupancy, capacity-occupancy);
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
		Assert.assertEquals(NUMBER_OF_TEST_STANDS_CONFIGURED, rows);
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
	public void thenAllStandsAreHighlightedAs(String highlightType)
	{
		List<WebElement> rows = tableViewPage.getTableRows();
		for(WebElement row : rows)
		{
			List<WebElement> cells = row.findElements(By.cssSelector("td"));
			if(highlightType.equalsIgnoreCase("full"))
			{
				// spaces should be red, third cell in row
				Assert.assertTrue("Cell is not styled correctly even though the stand is full", cells.get(2).getAttribute("class").contains("full"));
			}
			if(highlightType.equalsIgnoreCase("empty"))
			{
				// stands should be red, second cell in row
				Assert.assertTrue("Cell is not styled correctly even though the stand is empty", cells.get(1).getAttribute("class").contains("empty"));
			}
		}
	}
}
