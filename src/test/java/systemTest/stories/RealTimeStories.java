package systemTest.stories;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;
import org.junit.runner.RunWith;

import systemTest.tools.steps.CommonSteps;
import systemTest.tools.steps.PollingSteps;
import systemTest.tools.steps.RealTimeSteps;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class RealTimeStories extends JUnitStories
{
	public RealTimeStories()
	{
		configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true).doIgnoreFailureInView(true);
	}

	@Override
	public Configuration configuration()
	{
		Class<? extends Embeddable> embeddableClass = this.getClass();
		// Start from default ParameterConverters instance
		ParameterConverters parameterConverters = new ParameterConverters();
		// factory to allow parameter conversion and loading from external
		// resources (used by StoryParser too)
		ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters);
		// add custom converters
		parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")), new ExamplesTableConverter(examplesTableFactory));
		
		Properties viewResources = new Properties();
		viewResources.put("decorateNonHtml", "true");
		
		StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
				.withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
				.withDefaultFormats().withFormats(CONSOLE, Format.HTML)
				.withViewResources(viewResources);
		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(embeddableClass)).useStoryParser(new RegexStoryParser(examplesTableFactory))
				.useStoryReporterBuilder(reporterBuilder)
				.useParameterConverters(parameterConverters);
	}

	@Override
	public InjectableStepsFactory stepsFactory()
	{
		return new InstanceStepsFactory(configuration(), new CommonSteps(), new RealTimeSteps(), new PollingSteps());
	}

	@Override
	protected List<String> storyPaths()
	{
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded*.story");
	}
}