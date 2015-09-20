package service.eao;

import java.util.Date;

import model.NetworkSample;

public interface NetworkSampleEAO {
	public NetworkSample fetchLatestNetworkSample();

	public NetworkSample fetchSampleNearestTime(Date snapshotDate);
}
