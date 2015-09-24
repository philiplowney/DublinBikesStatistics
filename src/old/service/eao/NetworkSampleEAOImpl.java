package old.service.eao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;

import model.NetworkSample;
import model.StandSample;

@Stateless
public class NetworkSampleEAOImpl implements NetworkSampleEAO
{
	@Override
	public NetworkSample fetchLatestNetworkSample() {
		return makeRandomSample();
	}

	@Override
	public NetworkSample fetchSampleNearestTime(Date snapshotDate) {
		NetworkSample result =  makeRandomSample();
		result.setSampleTime(snapshotDate);
		return result;
	}
	
	private NetworkSample makeRandomSample() {
		NetworkSample result = new NetworkSample();
		result.setSampleTime(new Date(System.currentTimeMillis()));
		List<StandSample> standSamples = new ArrayList<>();
		result.setStandSamples(standSamples);
		for(int i=0; i<102; i++)
		{
			StandSample standSample = new StandSample();
			standSample.setStandNumber(i+1);
			standSample.setBikesAvailable(randInt(0,20));
			standSample.setPlacesAvailable(randInt(0,20));
			standSamples.add(standSample);
		}
		return result;
	}
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
