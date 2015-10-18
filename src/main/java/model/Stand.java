package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stand
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true, nullable=false, name="NUMBER_IN_NETWORK")
	private int numberInNetwork;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private double latitude;
	
	@Column(nullable=false)
	private double longitude;
	
	@Column(nullable=false)
	private int capacity;
	
	@Column(nullable=false)
	private int occupancy;
}
