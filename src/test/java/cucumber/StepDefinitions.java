package cucumber;

import dk.cybonspace.IsItFriday;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepDefinitions {
    private String today;
    private String actualAnswer;

    @When("^I ask whether it's work time yet \"(\\d+)\"")
    public boolean When(int workhour) throws Throwable {
        return workhour > 7 && workhour < 17;

    }
}
