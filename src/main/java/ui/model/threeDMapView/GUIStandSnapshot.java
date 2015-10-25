package ui.model.threeDMapView;


import java.io.Serializable;

import lombok.Data;
import model.StandDescription;

/**
 * Used by the GUI for mapping purposes
 * @author Philip Lowney
 *
 */
@Data
public class GUIStandSnapshot implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GUIStandSnapshot(){}
	public GUIStandSnapshot(StandDescription description, int spaces, int bikes)
	{
		this.description = description;
		currentSpaces = spaces;
		currentBikes = bikes;
	}
	private StandDescription description;
	private int currentSpaces;
	private int currentBikes;
}
