package service.model.jcdeceaux;

import lombok.Data;

/**
 * Represents a stand as delivered by the JCDeceaux webservice,
 i.e:
 * 	 
 {
 	"number":42,
	"name":"SMITHFIELD NORTH",
	"address":"Smithfield North",
	"position":{"lat":53.349562,"lng":-6.278198},
	"banking":true,
	"bonus":false,
	"status":"OPEN",
	"contract_name":"Dublin",
	"bike_stands":30,
	"available_bike_stands":4,
	"available_bikes":26,
	"last_update":1447618306000
}
 * @author Philip
 *
 */
@Data
public class JCDeceauxStandModel
{
	private int number;
	private String name;
	private String address;
	private Position position;
	private Boolean banking;
	private Boolean bonus;
	private Status status;
	private String contract_name;
	private Integer bike_stands;
	private Integer available_bike_stands;
	private Integer available_bikes;
	private long last_update; //TODO: This is a millis timestamp
	
	public enum Status
	{
		OPEN,
		CLOSED;
	}
	
	@Data
	public class Position
	{
		double lat;
		double lng;
	}
}
