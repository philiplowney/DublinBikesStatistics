package systemTest.tools.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import systemTest.tools.WebServiceMock;
import systemTest.tools.pageObjects.IndexPage;
import systemTest.tools.selenium.WebDriverManager;

public class CommonSteps
{
	private WebServiceMock testHarness = WebServiceMock.getInstance();

	@BeforeStories
	public void setUpEachStory()
	{
		//TODO: restore
		//testHarness.ensureStandsAreInSystem();
		new IndexPage().navToIndex();
	}

	@AfterStories
	public void tidyUpAfterStories()
	{
		WebDriverManager.getInstance().shutDown();
	}
}
