package systemTest.tools.pageObjects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.StandState;

import org.openqa.selenium.WebElement;

public class TableViewPage extends BasePage
{
	public static final String HEADING_STAND = "Stand";
	public static final String HEADING_BIKES = "Bikes";
	public static final String HEADING_SPACES = "Spaces";

	public int countRowsInTable()
	{
		return actionBot.waitAndThen().countRowsInOnlyTableOnScreen();
	}

	/**
	 * @return a map of stand names and states as displayed in the screen
	 */
	public Map<String, StandState> getDisplayedStandStates()
	{
		List<WebElement> tableRows = actionBot.waitAndThen().getRowsInOnlyTableInScreen();
		Map<String, StandState> result = new LinkedHashMap<>();
		for (WebElement rowElement : tableRows)
		{
			List<String> rowCellTexts = actionBot.splitRowIntoCellTexts(rowElement);
			result.put(rowCellTexts.get(0), new StandState(null, Integer.parseInt(rowCellTexts.get(1)), Integer.parseInt(rowCellTexts.get(2))));
		}
		return result;
	}


	public void sortTableByColumn(String column)
	{
		WebElement table = actionBot.waitAndThen().getOnlyTableInScreen();
		WebElement columnHeading = actionBot.getColumnHeadingCellFromTable(table, column);
		columnHeading.click();
	}

	public List<WebElement> getTableRows()
	{
		return actionBot.waitAndThen().getRowsInOnlyTableInScreen();
	}
}