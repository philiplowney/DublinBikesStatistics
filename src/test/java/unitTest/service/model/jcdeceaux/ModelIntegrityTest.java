package unitTest.service.model.jcdeceaux;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import service.model.jcdeceaux.JCDeceauxStandModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ModelIntegrityTest
{
	private static String realWorldJSONResponse;
	private Type listType = new TypeToken<ArrayList<JCDeceauxStandModel>>(){}.getType();
	
	@BeforeClass
	public static void setUp() throws IOException, URISyntaxException
	{
		Path sampleFilePath = Paths.get(ModelIntegrityTest.class.getClassLoader().getResource("sampleResponse.json").toURI());
		byte[] encodedLines = Files.readAllBytes(sampleFilePath);
		realWorldJSONResponse = new String(encodedLines, StandardCharsets.US_ASCII);
	}
	
	@Test
	public void theFileShouldDeSerialiseWithoutError()
	{
		new Gson().fromJson(realWorldJSONResponse, listType);
	}
	@Test
	public void thereShouldBeSeveralStandsInTheResult()
	{
		List<JCDeceauxStandModel> standModels = new Gson().fromJson(realWorldJSONResponse, listType);
		Assert.assertTrue(standModels.size()>90);
	}
	@Test
	public void theReturnedListShouldHaveNoBlanks()
	{
		List<JCDeceauxStandModel> standModels = new Gson().fromJson(realWorldJSONResponse, listType);
		for(JCDeceauxStandModel model : standModels)
		{
			Assert.assertNotNull(model.getAddress());
			Assert.assertNotNull(model.getName());
			Assert.assertNotEquals(0, model.getNumber());
			Assert.assertNotNull(model.getPosition());
			Assert.assertNotEquals(0, model.getPosition().getLat());
			Assert.assertNotEquals(0, model.getPosition().getLng());
			Assert.assertNotNull(model.getBonus());
			Assert.assertNotNull(model.getBanking());
			Assert.assertNotNull(model.getStatus());
			Assert.assertNotNull(model.getBike_stands());
			Assert.assertNotNull(model.getAvailable_bike_stands());
			Assert.assertNotNull(model.getAvailable_bikes());
			Assert.assertNotNull(model.getLast_update());
		}
	}
	@Test
	public void aReSerialisedVersionOfTheModelShouldMatchTheOriginalSampleString()
	{
		List<JCDeceauxStandModel> standModels = new Gson().fromJson(realWorldJSONResponse, listType);
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		String reSerialisedVersion = builder.create().toJson(standModels);
		Assert.assertTrue(realWorldJSONResponse.trim().equalsIgnoreCase(reSerialisedVersion.trim()));
	}
}
