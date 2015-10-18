package old.service.uiSupport;

import java.util.Date;
import java.util.List;

import lombok.Data;

//TODO: Make this an entity
@Data
public class NetworkSample {
	private Date sampleTime;
	private List<StandSample> standSamples;
}
