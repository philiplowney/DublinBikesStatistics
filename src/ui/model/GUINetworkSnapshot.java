package ui.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GUINetworkSnapshot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4908743706546179275L;
	private List<GUIStandSnapshot> standSnapshots;
	private Date captureTime;
}
