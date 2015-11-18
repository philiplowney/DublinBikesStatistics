package systemTest.tools;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import model.Stand;

/**
 * Utility class for calling the DB Analytics webservice for fetching network info
 * 
 * @author Philip
 *
 */
public class SystemTestWSHandler
{
	private Client client;
	private WebTarget webTarget;

	public SystemTestWSHandler(final String baseAddress)
	{
		client = ClientBuilder.newClient();
		webTarget = client.target(baseAddress);
	}

	public List<Stand> callListStands()
	{
		List<Stand> result = webTarget.path("fetchStands").request().accept(MediaType.TEXT_XML).get(new GenericType<List<Stand>>(){});
		return result;
	}

	public Stand callFetchStand(int standNumber)
	{
		Stand result = webTarget.path("fetchStand").path(""+standNumber).request().accept(MediaType.TEXT_XML).get(Stand.class);
		return result;
	}
}
