package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "stand_state")
public class StandState
{
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
	private long id;

	@OneToOne(mappedBy = "state")
	@JoinColumn(name = "stand")
	private Stand stand;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date sampleTime;

	@Column
	private int bikesAvailable;
	@Column
	private int placesAvailable;
}
