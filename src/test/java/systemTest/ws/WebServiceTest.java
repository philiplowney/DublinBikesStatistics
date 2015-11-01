package systemTest.ws;

import java.time.Instant;
import java.util.Date;

import model.Stand;
import model.StandState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import systemTest.tools.WebServiceHandler;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * Simple test of the webservice for creating/deleting stands (for, ironically,
 * test purposes...)
 * 
 * @author Philip
 *
 */
public class WebServiceTest
{
	public static final String BASE_REST_ADDRESS = "http://localhost:8080/DublinBikesAnalytics/rest/";
	private static WebServiceHandler wsHandler;
	private static final int TEST_STAND_NUMBER = 999;
	private static Stand testStand;

	@BeforeClass
	public static void setup()
	{
		testStand = new Stand(TEST_STAND_NUMBER, "test stand", "123 some street", 1, 1);
		testStand.setState(new StandState(Date.from(Instant.now()), 10, 10));
		wsHandler = new WebServiceHandler(BASE_REST_ADDRESS);
	}
	
	@Before
	public void wipeTestStand()
	{
		wsHandler.callDeleteStand(TEST_STAND_NUMBER);
	}

	@Test
	public void testCreate()
	{
		ClientResponse response = wsHandler.callCreateStand(testStand);
		Assert.assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDelete()
	{
		wsHandler.callCreateStand(testStand);
		ClientResponse response = wsHandler.callDeleteStand(TEST_STAND_NUMBER);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testFetchAll()
	{
		wsHandler.callCreateStand(testStand);
		int sizePreDeletion = wsHandler.callListStands().size();
		wsHandler.callDeleteStand(TEST_STAND_NUMBER);
		int sizePostDeletion = wsHandler.callListStands().size();
		
		Assert.assertTrue(sizePreDeletion>=1);
		Assert.assertEquals(sizePreDeletion-1, sizePostDeletion);
	}
	
	@Test
	public void testFetchStand()
	{
		wsHandler.callCreateStand(testStand);
		Stand fetchedStand = wsHandler.callFetchStand(TEST_STAND_NUMBER);
		Assert.assertEquals(testStand.getAddress(), fetchedStand.getAddress());
	}
	@Test
	public void testUpdate()
	{
		wsHandler.callCreateStand(testStand);
		int newNumBikes = 33;
		int newNumSpaces = 17;
		ClientResponse response = wsHandler.callUpdateStand(TEST_STAND_NUMBER, newNumBikes, newNumSpaces);
		Stand updatedStand = wsHandler.callFetchStand(TEST_STAND_NUMBER);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Assert.assertEquals(newNumBikes, updatedStand.getState().getBikesAvailable());
		Assert.assertEquals(newNumSpaces, updatedStand.getState().getPlacesAvailable());
	}
}
