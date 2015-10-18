package old.service.eao;

import java.util.Date;

import old.service.uiSupport.NetworkSample;

public interface NetworkSampleEAO {
	public NetworkSample fetchLatestNetworkSample();

	public NetworkSample fetchSampleNearestTime(Date snapshotDate);
}
