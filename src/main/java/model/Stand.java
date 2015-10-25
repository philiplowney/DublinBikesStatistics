package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Stand implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Stand()
	{
	}

	public Stand(int num, String name, String addr, double lat, double lng)
	{
		number = num;
		this.name = name;
		address = addr;
		latitude = lat;
		longitude = lng;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private int number;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private double latitude;
	@Column(nullable = false)
	private double longitude;
	@OneToOne(orphanRemoval = true)
	private StandState state;
}
