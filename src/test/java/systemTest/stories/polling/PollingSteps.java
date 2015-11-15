package systemTest.stories.polling;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.util.List;
import java.util.logging.Logger;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

import service.SystemProperties;
import service.SystemProperties.SystemProperty;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

public class PollingSteps
{
	private static final Logger LOGGER = Logger.getLogger(PollingSteps.class.getCanonicalName());
	private WireMockServer wireMockServer;

	@Given("a test JC Deceaux webservice is listening at http://localhost:$port")
	public void givenATestJCDeceauxWebserviceIsListeningAtHttplocalhost(int port)
	{
		WireMock.configureFor("localhost", port);
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(port));
		wireMockServer.start();
		System.out.println("Mocked webserver running on port "+port+"...");
		LOGGER.info("Dummy webserver set up to receive requests on port " + 9000);
		givenThat(get(urlMatching("/thing")).willReturn(aResponse().withStatus(200).withBody("mock stub thing is working")));
		System.out.println("Mock is working");
	}
	
	@Then("the webservice is polled periodically")
	public void  thenTheWebserviceIsPolledPeriodically() throws InterruptedException
	{
		String pollingPeriodProperty = SystemProperties.getInstance().getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_SECONDS);
		int pollingPeriodSeconds = Integer.parseInt(pollingPeriodProperty);
		LOGGER.info("Expecting to be polled every "+pollingPeriodSeconds+" seconds.");
		int numberOfPollsToWaitFor = 4;
		long millisecondsToSleep = (pollingPeriodSeconds*1000*numberOfPollsToWaitFor)+980l; 
		LOGGER.info("Going to sleep for exactly "+millisecondsToSleep+" to see how many webservice requests were received. Expecting "+numberOfPollsToWaitFor);
		WireMock.resetAllRequests();
		Thread.sleep(millisecondsToSleep);
		List<LoggedRequest> requests = WireMock.findAll(WireMock.getRequestedFor(urlMatching("/thing")));
		Assert.assertTrue("No requests received to webservice ", requests.size()>0);
		Assert.assertEquals("Wrong number of requests received ", numberOfPollsToWaitFor, requests.size());
	}
}
