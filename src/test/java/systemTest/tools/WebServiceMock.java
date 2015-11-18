package systemTest.tools;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import service.model.jcdeceaux.JCDeceauxStandModel;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WebServiceMock
{
	private static final int SERVER_PORT = 9000;
	private static final String SERVER_ADDRESS = "localhost";
	private static final Logger LOGGER = Logger.getLogger(WebServiceMock.class.getCanonicalName());
	private static WebServiceMock instance;	
	public static int NUMBER_OF_TEST_STANDS_CONFIGURED = 0;
	private Gson gson;
	public static final String URL_MATCHER_STATIONS_SERVICE = "/vls/v1/stations.*";
	
	private WebServiceMock()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		gson = builder.create();
		
		WireMock.configureFor(SERVER_ADDRESS, SERVER_PORT);
		WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(SERVER_PORT));
		wireMockServer.start();
		
		try
		{
			configureToReturn(generateRealWorldSample());
			LOGGER.info("Mock webservice set up at "+SERVER_ADDRESS+":"+SERVER_PORT+URL_MATCHER_STATIONS_SERVICE);
		}
		catch (URISyntaxException | IOException e)
		{
			LOGGER.log(Level.SEVERE, "Unable to initialise webservice mock for testing", e);
		}
	}
	
	public static WebServiceMock getInstance()
	{
		if(instance == null)
		{
			instance = new WebServiceMock();
		}
		return instance;
	}
	
	public void configureToReturn(List<JCDeceauxStandModel> stands)
	{
		givenThat(get(urlMatching(WebServiceMock.URL_MATCHER_STATIONS_SERVICE)).willReturn(aResponse().withStatus(200).withBody(gson.toJson(stands))));
	}
	
	public List<JCDeceauxStandModel> generateRealWorldSample() throws URISyntaxException, IOException
	{
		Path sampleFilePath = Paths.get(WebServiceMock.class.getClassLoader().getResource("sampleResponse.json").toURI());
		byte[] encodedLines = Files.readAllBytes(sampleFilePath);
		String realWorldJSONResponse = new String(encodedLines, StandardCharsets.US_ASCII);
		Type listType = new TypeToken<ArrayList<JCDeceauxStandModel>>(){}.getType();
		
		List<JCDeceauxStandModel> allStandModels = gson.fromJson(realWorldJSONResponse, listType);
		List<JCDeceauxStandModel> standsToReturn = new ArrayList<>();
		for(int i=0; i<10; i++)
		{
			standsToReturn.add(allStandModels.get(i));
		}
		return standsToReturn;
	}
}
