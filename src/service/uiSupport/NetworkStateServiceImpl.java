package service.uiSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.NetworkSample;
import model.StandSample;
import service.eao.NetworkSampleEAO;
import ui.model.GUINetworkSnapshot;
import ui.model.GUIStandSnapshot;
import constants.StandDescription;
import constants.StandDescriptionFetcher;

@Stateless
public class NetworkStateServiceImpl implements NetworkStateService
{
	@EJB
	private NetworkSampleEAO networkSampleEAO;
	
	@Override
	public GUINetworkSnapshot fetchCurrentSnapshot() {
		NetworkSample latestNetworkSample = networkSampleEAO.fetchLatestNetworkSample();
				
		return buildGUISnapshotFromSample(latestNetworkSample);
	}

	@Override
	public GUINetworkSnapshot fetchSnapShotNearTime(Date snapshotDate) {
		NetworkSample networkSample = networkSampleEAO.fetchSampleNearestTime(snapshotDate);
		
		return buildGUISnapshotFromSample(networkSample);
	}
	
	private GUINetworkSnapshot buildGUISnapshotFromSample(
			NetworkSample latestNetworkSample) {
		List<StandDescription> descriptions = StandDescriptionFetcher.getInstance().getDescriptions();
		List<GUIStandSnapshot> snapShots = new ArrayList<>();
		
		for(StandSample standSample : latestNetworkSample.getStandSamples())
		{
			for(StandDescription d : descriptions)
			{
				if(d.getNumber() == standSample.getStandNumber())
				{
					snapShots.add(new GUIStandSnapshot(d, standSample.getPlacesAvailable(), standSample.getBikesAvailable()));
					break;
				}
			}
		}
		
		GUINetworkSnapshot result = new GUINetworkSnapshot();
		result.setStandSnapshots(snapShots);
		result.setCaptureTime(new Date(System.currentTimeMillis())); // TODO: consult the DB to get the last check time
		return result;
	}
}
