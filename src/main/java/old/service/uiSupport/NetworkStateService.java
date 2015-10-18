package old.service.uiSupport;

import java.util.Date;

import ui.model.threeDMapView.GUINetworkSnapshot;

public interface NetworkStateService {
	GUINetworkSnapshot fetchCurrentSnapshot();

	GUINetworkSnapshot fetchSnapShotNearTime(Date snapshotDate);
}
