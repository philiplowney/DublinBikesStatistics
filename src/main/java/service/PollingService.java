package service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import service.SystemProperties.SystemProperty;

import javax.ws.rs.client.*;

@Startup
@Singleton
public class PollingService
{
	private static final Logger LOGGER = Logger.getLogger(PollingService.class.getCanonicalName());

	@Resource
	private TimerService timerService;

	private WebTarget webTarget;
	
	@PostConstruct
	public void start()
	{
		Client client = ClientBuilder.newClient();
		String webServiceAddress = SystemProperties.getInstance().getProperty(SystemProperty.REST_SERVICE_ADDRESS);
		webTarget = client.target(webServiceAddress);
		int pollingPeriodSeconds = Integer.parseInt(SystemProperties.getInstance()
				.getProperty(SystemProperty.REST_SERVICE_POLLING_PERIOD_SECONDS));
		timerService.createIntervalTimer(pollingPeriodSeconds*1000, pollingPeriodSeconds*1000, new TimerConfig());
		LOGGER.info("Shall start polling the webservice every "+pollingPeriodSeconds+" seconds...");
		
	}
	
	@Timeout
	public void pollWebService()
	{
		LOGGER.info("I am now polling the webservice...");
		try
		{
			String response = webTarget.request().get().toString();
			LOGGER.info("Response: "+response);
		}
		catch(Exception e)
		{
			LOGGER.log(Level.WARNING, "Unable to query the webservice...");
		}
	}
}
