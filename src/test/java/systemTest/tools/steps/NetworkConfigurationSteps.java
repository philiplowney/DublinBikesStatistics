package systemTest.tools.steps;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.junit.Test;

import service.model.jcdeceaux.JCDeceauxStandModel;
import systemTest.tools.WebServiceMock;
import constants.SystemProperties;
import constants.SystemProperties.SystemProperty;

public class NetworkConfigurationSteps
{
	private static final Logger LOGGER = Logger.getLogger(NetworkConfigurationSteps.class.getCanonicalName());
	
	private void sleepForPollingPeriodAndABit()
	{
		long pollingPeriodMillis = Long.parseLong(SystemProperties.getInstance().getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_MILLISECONDS));
		try
		{
			Thread.sleep(pollingPeriodMillis+500l);
		}
		catch (InterruptedException e)
		{
			LOGGER.log(Level.WARNING, "Unable to sleep for polling period", e);
		}
	}
	@Given("there are $numberOf stands")
	@Aliases(values = { 
			"the number of stands is increased to $numberOfStands", 
			"the number of stands is reduced to $numberOfStands"})
	public void givenThereArenumber_of_standsStands(int numberOfStands) throws URISyntaxException, IOException
	{
		WebServiceMock.getInstance().configureToReturn(WebServiceMock.getInstance().generateRealWorldSample(numberOfStands));
		sleepForPollingPeriodAndABit();
	}
	
	@Given("the bike stands have random capacity and occupancy")
	public void givenTheBikeStandsHaveRandomCapacityAndOccupancy() throws URISyntaxException, IOException
	{
		List<JCDeceauxStandModel> defaultStandsAltered = WebServiceMock.getInstance().generateRealWorldSample();
		
		Random rand = new Random();
		for(JCDeceauxStandModel stand : defaultStandsAltered)
		{
			int spaces = rand.nextInt(21);
			int bikes = rand.nextInt(21);
			stand.setAvailable_bikes(bikes);
			stand.setAvailable_bike_stands(spaces);
		}
		WebServiceMock.getInstance().configureToReturn(defaultStandsAltered);
		sleepForPollingPeriodAndABit();
		LOGGER.info("Mock webservice configured to return randomised stand states for "+defaultStandsAltered.size()+" stands");
	}

	@Given("all the bike stands currently have a capacity of $capacity and an occupancy of $occupancy")
	public void givenAllTheBikeStandsCurrentlyHaveACapacityOfAndAnOccupancyOf(int capacity, int occupancy) throws URISyntaxException, IOException
	{
		List<JCDeceauxStandModel> defaultStandsAltered = WebServiceMock.getInstance().generateRealWorldSample();
		for(JCDeceauxStandModel stand : defaultStandsAltered)
		{
			stand.setAvailable_bikes(occupancy);
			stand.setAvailable_bike_stands(capacity-occupancy);
		}
		WebServiceMock.getInstance().configureToReturn(defaultStandsAltered);
		sleepForPollingPeriodAndABit();
		LOGGER.info("Stands configured with capacity of "+capacity+" and occupancy of "+occupancy);
	}
}
