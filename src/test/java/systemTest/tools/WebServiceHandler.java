package systemTest.tools;

import java.util.List;

import javax.ws.rs.core.MediaType;

import model.Stand;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

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
	private WebResource webResource;

	public WebServiceHandler(final String baseAddress)
	{
		client = Client.create();
		webResource = client.resource(baseAddress);
	}

	public ClientResponse callCreateStand(final Stand testStand)
	{
		ClientResponse response = webResource.path("addNewStand").type(MediaType.TEXT_XML).put(ClientResponse.class, testStand);
		return response;
	}

	public ClientResponse callDeleteStand(final int testStandNumber)
	{
		ClientResponse response = webResource.path("deleteStand").path("" + testStandNumber).delete(ClientResponse.class);
		return response;
	}

	public List<Stand> callListStands()
	{
		List<Stand> result = webResource.path("fetchStands").getRequestBuilder().accept(MediaType.TEXT_XML).get(new GenericType<List<Stand>>(){});
		return result;
	}

	public Stand callFetchStand(int standNumber)
	{
		Stand result = webResource.path("fetchStand").path(""+standNumber).getRequestBuilder().accept(MediaType.TEXT_XML).get(Stand.class);
		return result;
	}

	public ClientResponse callUpdateStand(int testStandNumber, int newNumBikes, int newNumSpaces)
	{
		ClientResponse response = webResource.path("updateStand").path(""+testStandNumber).path(""+newNumBikes).path(""+newNumSpaces).put(ClientResponse.class);
		return response;
	}
}
