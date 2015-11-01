package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessType;

import lombok.Data;

@Data
@Entity
@NamedQuery(name = Stand.NAMED_QUERY_FIND_BY_NUMBER, query = "SELECT s FROM Stand s WHERE s.number = :" + Stand.QUERY_PARAMETER_STAND_NUMBER)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
	@XmlTransient
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
	@OneToOne(orphanRemoval = true, cascade=CascadeType.ALL)
	private StandState state;

	public static final String QUERY_PARAMETER_STAND_NUMBER = "number";

	public static final String NAMED_QUERY_FIND_BY_NUMBER = "Stand.findByNumber";
}
