package ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import service.uiSupport.NetworkStateService;
import ui.model.GUINetworkSnapshot;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@ManagedBean
@ViewScoped
public class IndexUI {
	@Getter
	private GUINetworkSnapshot networkSnapshot;
	
	@Getter
	@Setter
	private Date snapshotDate;
	
	@EJB
	private NetworkStateService networkService;
	
	@Getter
	@Setter
	private ViewMode viewMode = ViewMode.MAP;
	
	@PostConstruct
	public void init()
	{
		networkSnapshot = networkService.fetchCurrentSnapshot();
		snapshotDate = networkSnapshot.getCaptureTime();
	}
	
	public void clickChangeTime()
	{
		networkSnapshot = networkService.fetchSnapShotNearTime(snapshotDate);
		snapshotDate = networkSnapshot.getCaptureTime();
	}
	
	public String fetchCurrentStandInformationJSON() throws JsonIOException, JsonSyntaxException, FileNotFoundException
	{	
		return new Gson().toJson(networkSnapshot.getStandSnapshots());
	}
	
	public List<SelectItem> getViewModes()
	{
		return ViewMode.asSelectList();
	}
	public enum ViewMode
	{
		MAP,
		TABLE;
		public static List<SelectItem> asSelectList()
		{
			List<SelectItem> result = new ArrayList<>();
			for(ViewMode vm : values())
			{
				result.add(new SelectItem(vm, vm.toString()));
			}
			return result;
		}
	}
}