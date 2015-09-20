package constants;

import java.io.Serializable;

import lombok.Data;

@Data
public class StandDescription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StandDescription(int num, String name, String addr, String lat, String lng)
	{
		number = num;
		this.name = name;
		address = addr;
		latitude = lat;
		longitude = lng;
	}
	private int number;
	private String name;
	private String address;
	private String latitude;
	private String longitude;
}
