package service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import model.Stand;
import constants.SystemProperties;
import constants.SystemProperties.SystemProperty;

@Startup
@Singleton
public class PollingService
{
	private static final Logger LOGGER = Logger.getLogger(PollingService.class.getCanonicalName());

	@Resource
	private TimerService timerService;
	
	@EJB
	private UpdateService updateService;

	private Timer timer;
	private WebTarget jcDeceauxAPIBaseTarget;
	
	private String apiKey;
	private String contractName;

	private String webServiceAddress;
	
	@PostConstruct
	public void start()
	{
		// To reduce the minimum polling interval below 1,000 ms, see the Glassfish tip here:
		// https://docs.oracle.com/javaee/6/tutorial/doc/bnboy.html
		Client client = ClientBuilder.newClient();
		SystemProperties props = SystemProperties.getInstance();
		apiKey = props.getProperty(SystemProperty.API_KEY);
		contractName = props.getProperty(SystemProperty.CONTRACT_NAME);
		webServiceAddress = props.getProperty(SystemProperty.REST_SERVICE_ADDRESS);
		jcDeceauxAPIBaseTarget = client.target(webServiceAddress);
		long pollingPeriodMilliseconds = Long.parseLong(props
				.getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_MILLISECONDS));
		timer = timerService.createIntervalTimer(pollingPeriodMilliseconds, pollingPeriodMilliseconds, new TimerConfig());
		LOGGER.info("Shall start polling the webservice every "+pollingPeriodMilliseconds+" milliseconds...");
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
		String jsonResponse = null;
		try
		{
			WebTarget fullTarget = jcDeceauxAPIBaseTarget.path("stations").queryParam("contract", contractName).queryParam("apiKey", apiKey);
			jsonResponse = fullTarget.request().get(String.class);
		}
		catch(Exception e)
		{
			LOGGER.log(Level.WARNING, "Unable to query the webservice...");
			return;
		}

		List<Stand> polledSnapshot = new JCDeauxPollingResult(jsonResponse).toStands();
		updateService.receiveBulkUpdate(polledSnapshot);
	}
}
