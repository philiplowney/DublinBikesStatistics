package systemTest.tools.pageObjects;

import systemTest.tools.selenium.ActionBot;

public class LeftMenu
{
	private ActionBot actionBot = new ActionBot();
	
	public TableViewPage navRealTimeTableView()
	{
		actionBot.clickLeftMenuItem("Table");
		return new TableViewPage();
	}

	public RealtimeDashboardPage navToRealtimeDashboard()
	{
		actionBot.clickLeftMenuItem("Dashboard");
		return new RealtimeDashboardPage();
	}
}
