package systemTest.tools.pageObjects;

import org.junit.Test;
import org.openqa.selenium.By;

public class RealtimeDashboardPage extends BasePage
{
	@Test // for during dev of this Page Object only. Tis handy.
	public void checkPageIsWorking()
	{
		RealtimeDashboardPage dashboard = new IndexPage().navToIndex().getLeftMenu().navToRealtimeDashboard();
		int bikesShown = dashboard.getTotalBikesCurrentlyAvailable();
		int spacesShown = dashboard.getTotalSpacesCurrentlyAvailable();
		System.out.println("Bikes: "+bikesShown);
		System.out.println("Spaces: "+spacesShown);
	}
	public int getTotalSpacesCurrentlyAvailable()
	{
		String textValue = actionBot.waitAndThen().getWebDriver().findElement(By.id("totalSpacesAvailable")).getText().split(": ")[1];
		return Integer.parseInt(textValue);
	}
	public int getTotalBikesCurrentlyAvailable()
	{
		String textValue = actionBot.waitAndThen().getWebDriver().findElement(By.id("totalBikesAvailable")).getText().split(": ")[1];
		return Integer.parseInt(textValue);
	}

}
