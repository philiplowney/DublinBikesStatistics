package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.Stand;
import model.StandState;
import service.model.jcdeceaux.JCDeceauxStandModel;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JCDeauxPollingResult
{
	private List<JCDeceauxStandModel> polledStandsInJCDFormat;

	/**
	 * A JSON-encoded string of the response from the JCD API
	 * @param jsonResponse
	 */
	public JCDeauxPollingResult(String jsonResponse)
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		Type listType = new TypeToken<ArrayList<JCDeceauxStandModel>>(){}.getType();
		polledStandsInJCDFormat = gsonBuilder.create().fromJson(jsonResponse, listType);
	}

	public List<Stand> toStands()
	{
		List<Stand> result = new ArrayList<>();
		for(JCDeceauxStandModel model : polledStandsInJCDFormat)
		{
			Stand equivilentStand = new Stand(model.getNumber(), model.getName(), model.getAddress(), model.getPosition().getLat(), model.getPosition().getLng());
			equivilentStand.setState(new StandState(null, model.getAvailable_bikes(), model.getAvailable_bike_stands()));
			result.add(equivilentStand);
		}
		return result;
	}

	
}
