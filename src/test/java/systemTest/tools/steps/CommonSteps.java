package systemTest.tools.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import systemTest.tools.WebServiceMock;
import systemTest.tools.pageObjects.IndexPage;
import systemTest.tools.selenium.WebDriverManager;

public class CommonSteps
{
	@BeforeStories
	public void setUpEachStory()
	{
		WebServiceMock.getInstance();
		new IndexPage().navToIndex();
	}

	@AfterStories
	public void tidyUpAfterStories()
	{
		WebDriverManager.getInstance().shutDown();
	}
}
