package service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import constants.SystemProperties;
import constants.SystemProperties.SystemProperty;

@Startup
@Singleton
public class PollingService
{
	private static final Logger LOGGER = Logger.getLogger(PollingService.class.getCanonicalName());

	@Resource
	private TimerService timerService;

	private Timer timer;
	private WebTarget jcDeceauxAPIBaseTarget;
	
	private String apiKey;
	private String contractName;

	private String webServiceAddress;
	
	@PostConstruct
	public void start()
	{
		Client client = ClientBuilder.newClient();
		SystemProperties props = SystemProperties.getInstance();
		apiKey = props.getProperty(SystemProperty.API_KEY);
		contractName = props.getProperty(SystemProperty.CONTRACT_NAME);
		webServiceAddress = props.getProperty(SystemProperty.REST_SERVICE_ADDRESS);
		jcDeceauxAPIBaseTarget = client.target(webServiceAddress);
		int pollingPeriodSeconds = Integer.parseInt(props
				.getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_SECONDS));
		timer = timerService.createIntervalTimer(pollingPeriodSeconds*1000, pollingPeriodSeconds*1000, new TimerConfig());
		LOGGER.info("Shall start polling the webservice every "+pollingPeriodSeconds+" seconds...");
		
	}
	
	@PreDestroy
	public void stop()
	{
		timer.cancel();
		LOGGER.info("Polling ceased as container is being shut down");
	}
	
	@Timeout
	public void pollWebServiceForStations()
	{
		try
		{
			WebTarget fullTarget = jcDeceauxAPIBaseTarget.path("stations").queryParam("contract", contractName).queryParam("apiKey", apiKey);
			LOGGER.info("I am now polling the webservice @ "+fullTarget.getUri());
			String response = fullTarget.request().get(String.class);
			LOGGER.info("Response: "+response);
		}
		catch(Exception e)
		{
			LOGGER.log(Level.WARNING, "Unable to query the webservice...");
		}
	}
}
