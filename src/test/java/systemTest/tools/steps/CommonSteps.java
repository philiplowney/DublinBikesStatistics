package systemTest.tools.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import systemTest.tools.SystemTestHarness;
import systemTest.tools.selenium.WebDriverManager;

public class CommonSteps
{
	private SystemTestHarness testHarness = new SystemTestHarness();

	@BeforeStories
	public void setUpEachStory()
	{
		testHarness.ensureStandsAreInSystem();
		testHarness.navigateToHomepage();
	}

	@AfterStories
	public void tidyUpAfterStories()
	{
		WebDriverManager.getInstance().shutDown();
	}
}
