package service.uiSupport;

import java.util.Date;

import ui.model.GUINetworkSnapshot;

public interface NetworkStateService {
	GUINetworkSnapshot fetchCurrentSnapshot();

	GUINetworkSnapshot fetchSnapShotNearTime(Date snapshotDate);
}
