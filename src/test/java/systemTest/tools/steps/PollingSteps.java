package systemTest.tools.steps;

import java.util.logging.Logger;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import systemTest.tools.WebServiceMock;

import com.github.tomakehurst.wiremock.client.WireMock;

import constants.SystemProperties;
import constants.SystemProperties.SystemProperty;

public class PollingSteps
{
	private static final Logger LOGGER = Logger.getLogger(PollingSteps.class.getCanonicalName());
	
	@Given("a test JC Deceaux webservice is listening")
	public void givenATestJCDeceauxWebserviceIsListening()
	{
		WebServiceMock.getInstance();
	}

	@Given("the webservice has been polled")
	public void givenTheWebserviceHasBeenPolled() {
		// take this as a given
	}
	
	@Then("the webservice is polled periodically")
	public void  thenTheWebserviceIsPolledPeriodically() throws InterruptedException
	{
		String pollingPeriodProperty = SystemProperties.getInstance().getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_MILLISECONDS);
		int pollingPeriodMilliseconds = Integer.parseInt(pollingPeriodProperty);
		LOGGER.info("Expecting to be polled every "+pollingPeriodMilliseconds+" seconds.");
		int numberOfPollsToWaitFor = 4;
		long millisecondsToSleep = (pollingPeriodMilliseconds*numberOfPollsToWaitFor)+10l; 
		LOGGER.info("Going to sleep for exactly "+millisecondsToSleep+"ms to see how many webservice requests were received. Expecting "+numberOfPollsToWaitFor);
		WireMock.resetAllRequests();
		Thread.sleep(millisecondsToSleep);
		WireMock.verify(numberOfPollsToWaitFor, WireMock.getRequestedFor(WireMock.urlMatching(WebServiceMock.URL_MATCHER_STATIONS_SERVICE)));
	}
}
