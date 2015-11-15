package systemTest.tools;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Stand;

/**
 * Utility class for calling the DB Analytics webservice for adding, deleting &
 * updating stands.
 * 
 * @author Philip
 *
 */
public class WebServiceHandler
{
	private Client client;
	private WebTarget webTarget;

	public WebServiceHandler(final String baseAddress)
	{
		client = ClientBuilder.newClient();
		webTarget = client.target(baseAddress);
	}

	public Response callCreateStand(final Stand testStand)
	{
		Response response = webTarget
				.path("addNewStand")
				.request()
				.put(Entity.entity(testStand, MediaType.TEXT_XML), Response.class);
		return response;
	}

	public Response callDeleteStand(final int testStandNumber)
	{
		Response response = webTarget.path("deleteStand").path("" + testStandNumber).request().delete();
		return response;
	}

	public List<Stand> callListStands()
	{
		List<Stand> result = webTarget.path("fetchStands").request().get(new GenericType<List<Stand>>(){});
		return result;
	}

	public Stand callFetchStand(int standNumber)
	{
		Stand result = webTarget.path("fetchStand").path(""+standNumber).request().accept(MediaType.TEXT_XML).get(Stand.class);
		return result;
	}

	public Response callUpdateStand(int testStandNumber, int newNumBikes, int newNumSpaces)
	{
		Response response = webTarget.path("updateStand").path(""+testStandNumber).path(""+newNumBikes).path(""+newNumSpaces)
				.request()
				.get();
		return response;
	}
}
