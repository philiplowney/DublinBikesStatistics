package systemTest.tools.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ActionBot
{
	protected WebDriver webDriver = WebDriverManager.getInstance().getDriver();

	public void clickLeftMenuItem(String menuText)
	{
		WebElement href = webDriver.findElement(By.xpath("//*[@id='menu']/li/a[text()='"+menuText+"']"));
		href.click();
	}

	public void navToHomePage()
	{
		webDriver.get("http://localhost:8080/DublinBikesAnalytics");
	}
}
