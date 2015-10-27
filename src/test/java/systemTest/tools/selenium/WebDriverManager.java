package systemTest.tools.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManager
{
	private static WebDriverManager instance;
	private WebDriver driver;
	
	public WebDriver getDriver()
	{
		return driver;
	}
	public static WebDriverManager getInstance()
	{
		if(instance == null)
		{
			instance = new WebDriverManager();
		}
		return instance;
	}
	private WebDriverManager()
	{
		driver = new FirefoxDriver();
	}
}
