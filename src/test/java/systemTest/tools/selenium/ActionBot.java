package systemTest.tools.selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;

public class ActionBot
{
	private static final Logger LOGGER = Logger.getLogger(ActionBot.class.getCanonicalName());
	
	private static final String XPATH_ANY_TABLE = "//*[contains(@class, 'ui-datatable-tablewrapper')]";
	private static final String XPATH_ALL_ROWS_IN_TABLE = XPATH_ANY_TABLE+"//tbody//tr";

	@Getter
	protected WebDriver webDriver = WebDriverManager.getInstance().getDriver();

	/**
	 * Based on code here:
	 * http://stackoverflow.com/questions/23300126/selenium-wait-for-primefaces-4-0-ajax-to-process
	 */
	public ActionBot waitAndThen()
	{
		List<Class<? extends Exception>> allowableExceptions = new ArrayList<>();
		allowableExceptions.addAll(Arrays.asList(NoSuchElementException.class, ElementNotFoundException.class, ElementNotVisibleException.class));
		WebDriver driver = WebDriverManager.getInstance().getDriver();
		Wait<WebDriver> patientlyWait = new FluentWait<WebDriver>(driver)
				.withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(50, TimeUnit.MILLISECONDS)
				.ignoreAll(allowableExceptions);
		
		Function<WebDriver, Boolean> ajaxIsFinished = new Function<WebDriver, Boolean>() {
			private boolean pageInActive = true;
			private boolean previousPageInactive = true;

			private boolean executeBooleanJavascript(WebDriver input, String javascript)
			{
				return (Boolean) ((JavascriptExecutor) input).executeScript(javascript);
			}
			
			@Override
			public Boolean apply(WebDriver input)
			{
				boolean jQueryDefined = executeBooleanJavascript(input, "return typeof jQuery != 'undefined';");
	            boolean primeFacesDefined = executeBooleanJavascript(input, "return typeof PrimeFaces != 'undefined';");
	            
				boolean jQueryNotRunning = !jQueryDefined || executeBooleanJavascript(input, "return jQuery.active == 0;");
				boolean primefacesNotRunning = !primeFacesDefined || executeBooleanJavascript(input, "return PrimeFaces.ajax.Queue.isEmpty();");
				boolean pageLoaded = executeBooleanJavascript(input, "return document.readyState == 'complete';");

				pageInActive = primefacesNotRunning && jQueryNotRunning && pageLoaded;
				if (!pageInActive && (previousPageInactive != pageInActive))
				{
					LOGGER.info("Waiting for ajax request finish before proceeding...");
				}
				previousPageInactive = pageInActive;
				return pageInActive;
			}
		};
		try
		{
			patientlyWait.until(ajaxIsFinished);
			return this;
		}
		catch (TimeoutException toe)
		{
			LOGGER.log(Level.WARNING, "Ajax seems to be stuck - timed out waiting for it to finish", toe);
			throw toe;
		}
	}
	
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
