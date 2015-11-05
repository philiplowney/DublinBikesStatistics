package systemTest.tools.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ActionBot
{
	private static final String XPATH_ANY_TABLE = "//*[contains(@class, 'ui-datatable-tablewrapper')]";
	private static final String XPATH_ALL_ROWS_IN_TABLE = XPATH_ANY_TABLE+"//tbody//tr";
	protected WebDriver webDriver = WebDriverManager.getInstance().getDriver();

	public void clickLeftMenuItem(String menuText)
	{
		WebElement href = webDriver.findElement(By.xpath("//*[@id='menu']/li/a[text()='" + menuText + "']"));
		href.click();
	}

	public void navToHomePage()
	{
		webDriver.get("http://localhost:8080/DublinBikesAnalytics");
	}

	public int countRowsInOnlyTableOnScreen()
	{
		List<WebElement> rows = getRowsInOnlyTableInScreen();
		return rows.size();
	}

	public List<WebElement> getRowsInOnlyTableInScreen()
	{
		return webDriver.findElements(By.xpath(XPATH_ALL_ROWS_IN_TABLE));
	}

	public List<String> splitRowIntoCellTexts(WebElement rowElement)
	{
		List<WebElement> cells = rowElement.findElements(By.xpath("td"));
		List<String> result = new ArrayList<>();
		for (WebElement cell : cells)
		{
			result.add(cell.getText());
		}
		return result;
	}

	public WebElement getOnlyTableInScreen()
	{
		return webDriver.findElement(By.xpath(XPATH_ANY_TABLE));
	}

	public WebElement getColumnHeadingCellFromTable(WebElement table, String column)
	{
		return table.findElement(By.xpath("//thead//tr//th//*[text() = '"+column+"']/.."));
	}
}
