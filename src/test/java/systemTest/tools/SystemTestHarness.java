package systemTest.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Stand;
import systemTest.tools.pageObjects.IndexPage;

public class SystemTestHarness
{
	private static final Logger LOGGER = Logger.getLogger(SystemTestHarness.class.getCanonicalName());
	
	public WebServiceHandler wsHandler = new WebServiceHandler(APPUPDATE_REST_ADDRESS);

	public static final String APPUPDATE_REST_ADDRESS = "http://localhost:8080/DublinBikesAnalytics/rest/";
	
	public void placeStandsIntoSystem()
	{
		// Delete existing stands
		List<Stand> allExtantStands = wsHandler.callListStands();
		if(!allExtantStands.isEmpty())
		{
			for(Stand extantStandToDelete: allExtantStands)
			{
				wsHandler.callDeleteStand(extantStandToDelete.getNumber());
			}
		}
		// Load the standard stand list from Json file
		List<Stand> stands = StandDescriptionFetcher.getInstance().getDescriptions();
		List<Stand> subList = new ArrayList<>();
		for(int i=0; i<stands.size(); i+=10)
		{
			subList.add(stands.get(i));
		}
		// Save a small, test-version of the list of stands
		for(Stand stand : subList)
		{
			wsHandler.callCreateStand(stand);
		}
		LOGGER.info("Stands saved in network - total quantity: "+subList.size());
	}

	public void navigateToHomepage()
	{
		new IndexPage().navToIndex();
		LOGGER.info("Navigated to home page");
	}

	public WebServiceHandler getWebServiceUpdater()
	{
		return wsHandler;
	}

}
