package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;

@Data
@Entity
@Table(name = "stand_state")
@XmlAccessorType(XmlAccessType.FIELD)
public class StandState implements Serializable
{
	private static final long serialVersionUID = 1L;

	public StandState()
	{
	}

	public StandState(Date sampleTime, int bikesAvailable, int placesAvailable)
	{
		super();
		this.sampleTime = sampleTime;
		this.bikesAvailable = bikesAvailable;
		this.placesAvailable = placesAvailable;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private long id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@XmlTransient // too much work right now: solution possibly here though: http://stackoverflow.com/questions/2519432/jaxb-unmarshal-timestamp
	private Date sampleTime;

	@Column
	private int bikesAvailable;
	@Column
	private int placesAvailable;
}
