package systemTest.tools.pageObjects;

import systemTest.tools.selenium.ActionBot;

public abstract class BasePage
{
	protected ActionBot actionBot = new ActionBot();
	
	private LeftMenu leftMenu = new LeftMenu();
	
	public LeftMenu getLeftMenu()
	{
		return leftMenu;
	}

	public IndexPage navToIndex()
	{
		actionBot.navToHomePage();
		return new IndexPage();
	}
}
