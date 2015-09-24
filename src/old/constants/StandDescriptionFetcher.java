package old.constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class StandDescriptionFetcher {
	private static final Logger LOGGER = Logger.getLogger(StandDescriptionFetcher.class.getCanonicalName());
	
	private static StandDescriptionFetcher instance = null;
	public static StandDescriptionFetcher getInstance()
	{
		if(instance == null)
		{
			instance = new StandDescriptionFetcher();
		}
		return instance;
	}
	
	private List<StandDescription> descriptions;
	
	protected StandDescriptionFetcher()
	{
		try
		{
			Type listType = new TypeToken<ArrayList<StandDescription>>() {}.getType();
			URL url = getClass().getResource("StandDescriptions.json");
			File file = new File(url.getPath());
			descriptions = new Gson().fromJson(new FileReader(file), listType);
		}
		catch(JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			LOGGER.log(Level.INFO, "Unable to get file describing stations", e);
			descriptions = new ArrayList<StandDescription>();
		}
	}
	
	public List<StandDescription> getDescriptions()
	{
		return descriptions;
	}
}
