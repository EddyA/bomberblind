package glue;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {
                "classpath:cukes",
        },
        strict = true,
        tags = { "@UnitTest" }
)
public class CukeRunnerTest {
}
