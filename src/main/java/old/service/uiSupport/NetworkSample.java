package old.service.uiSupport;

import java.util.Date;
import java.util.List;

import lombok.Data;
import model.StandState;

//TODO: Make this an entity
@Data
public class NetworkSample {
	private Date sampleTime;
	private List<StandState> standSamples;
}
