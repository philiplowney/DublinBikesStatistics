package systemTest.tools.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.util.logging.Logger;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import constants.SystemProperties;
import constants.SystemProperties.SystemProperty;

public class PollingSteps
{
	private static final String URL_MATCHER_STATIONS_SERVICE = "/vls/v1/stations.*";
	private static final Logger LOGGER = Logger.getLogger(PollingSteps.class.getCanonicalName());
	private WireMockServer wireMockServer;

	@Given("a test JC Deceaux webservice is listening")
	public void givenATestJCDeceauxWebserviceIsListening()
	{
		WireMock.configureFor("localhost", 9000);
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(9000));
		wireMockServer.start();
		givenThat(get(urlMatching(URL_MATCHER_STATIONS_SERVICE)).willReturn(aResponse().withStatus(200).withBody("mock stub thing is working")));
	}
	
	@Given("the test JC Deceaux webservice says all stands are have a capacity of <capacity> and an occupancy of <occupancy>")
	public void givenTheTestJCDeceauxWebserviceSaysAllStandsAreHaveACapacityOfcapacityAndAnOccupancyOfoccupancy() {
	  // PENDING
	}

	@Given("the webservice has been polled")
	public void givenTheWebserviceHasBeenPolled() {
	  // PENDING
	}
	
	@Then("the webservice is polled periodically")
	public void  thenTheWebserviceIsPolledPeriodically() throws InterruptedException
	{
		String pollingPeriodProperty = SystemProperties.getInstance().getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_SECONDS);
		int pollingPeriodSeconds = Integer.parseInt(pollingPeriodProperty);
		LOGGER.info("Expecting to be polled every "+pollingPeriodSeconds+" seconds.");
		int numberOfPollsToWaitFor = 4;
		long millisecondsToSleep = (pollingPeriodSeconds*1000*numberOfPollsToWaitFor)+10l; 
		LOGGER.info("Going to sleep for exactly "+millisecondsToSleep+" to see how many webservice requests were received. Expecting "+numberOfPollsToWaitFor);
		WireMock.resetAllRequests();
		Thread.sleep(millisecondsToSleep);
		WireMock.verify(numberOfPollsToWaitFor, WireMock.getRequestedFor(WireMock.urlMatching(URL_MATCHER_STATIONS_SERVICE)));
		
	}
}
