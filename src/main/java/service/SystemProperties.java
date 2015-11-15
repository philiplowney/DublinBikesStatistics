package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemProperties
{
	public enum SystemProperty
	{
		REST_SERVICE_ADDRESS("restservice.address"),
		REST_SERVICE_POLLING_PERIOD_SECONDS("restservice.polling.period");
		
		private String string;

		SystemProperty(String string)
		{
			this.string = string;
		}
		@Override
		public String toString()
		{
			return string;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(SystemProperties.class.getCanonicalName());
	private static SystemProperties instance;
	
	private Properties properties;
	
	private SystemProperties() throws FileNotFoundException, IOException
	{
		properties = new Properties();
		properties.load(new FileInputStream("c:\\dublinBikes.properties"));
	}
	
	public static SystemProperties getInstance()
	{
		if(instance == null)
		{
			try
			{
				instance = new SystemProperties();
			}
			catch (IOException e)
			{
				LOGGER.log(Level.SEVERE, "Unable to load system properties file", e);
			}
		}
		return instance;
	}
	
	public String getProperty(SystemProperty sysProp)
	{
		return properties.getProperty(sysProp.toString());
	}
}
