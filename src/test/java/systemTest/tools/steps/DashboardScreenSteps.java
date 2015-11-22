package systemTest.tools.steps;

import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import systemTest.tools.pageObjects.IndexPage;
import systemTest.tools.pageObjects.RealtimeDashboardPage;

public class DashboardScreenSteps
{
	private RealtimeDashboardPage dashboardPage;

	@When("the user navigates to the dashboard screen")
	public void navigateToDashboardScreen()
	{
		dashboardPage = new IndexPage().getLeftMenu().navToRealtimeDashboard();
	}

	@Then("the bikes-to-spaces piechart will show $numberOfBikesExpected total available bikes")
	public void thenTheBikestospacesPiechartWillShowtotal_bikesTotalAvailableBikes(int numberOfBikesExpected)
	{
		int numberOfBikesShown = dashboardPage.getTotalBikesCurrentlyAvailable();
		Assert.assertEquals(numberOfBikesExpected, numberOfBikesShown);
	}

	@Then("the bikes-to-spaces piechart will show $numberOfFreeStandsExpected total available stands")
	public void thenTheBikestospacesPiechartWillShowtotal_free_standsTotalAvailableStands(int numberOfFreeStandsExpected)
	{
		int numberOfFreeStandsShown = dashboardPage.getTotalSpacesCurrentlyAvailable();
		Assert.assertEquals(numberOfFreeStandsExpected, numberOfFreeStandsShown);
	}
}
