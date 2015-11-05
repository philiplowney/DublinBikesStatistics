package systemTest.tools.selenium;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManager
{
	public void getFilePath()
	{
	}
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
	public WebDriverManager()
	{
		URL driverPath = WebDriverManager.class.getClassLoader().getResource("chromedriver.exe");
		
//		System.setProperty("webdriver.chrome.driver", driverPath.getPath());
//		driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
	}
}
