package systemTest.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Stand;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class StandDescriptionFetcher
{
	private static final Logger LOGGER = Logger.getLogger(StandDescriptionFetcher.class.getCanonicalName());
	
	private static StandDescriptionFetcher instance = null;

	public static StandDescriptionFetcher getInstance()
	{
		if (instance == null)
		{
			instance = new StandDescriptionFetcher();
		}
		return instance;
	}

	private List<Stand> descriptions;

	private StandDescriptionFetcher()
	{
		try
		{
			Type listType = new TypeToken<ArrayList<Stand>>()
			{
			}.getType();
			URL url = getClass().getClassLoader().getResource("StandDescriptions.json");
			File file = new File(url.getPath());
			descriptions = new Gson().fromJson(new FileReader(file), listType);
		}
		catch (JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			LOGGER.log(Level.INFO, "Unable to get file describing stations", e);
			descriptions = new ArrayList<Stand>();
		}
	}

	public List<Stand> getDescriptions()
	{
		return descriptions;
	}
}
