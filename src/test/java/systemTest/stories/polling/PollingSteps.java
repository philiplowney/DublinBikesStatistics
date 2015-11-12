package systemTest.stories.polling;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class PollingSteps
{
	@Given("a test JC Deceaux webservice is listening at http://localhost:9000")
	@Pending
	public void givenATestJCDeceauxWebserviceIsListeningAtHttplocalhost9000()
	{
		// PENDING
	}

	@When("the system is deployed")
	public void whenTheSystemIsDeployed()
	{
		// let's assume this is always the case
	}

	@Then("the webservice is polled on a regular basis")
	@Pending
	public void thenTheWebserviceIsPolledOnARegularBasis()
	{
		// PENDING
	}
}
