package cucumber;



import dto.Employee;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Date;

import static org.junit.Assert.*;

public class StepDefinitions {
    private String today;
    private String actualAnswer;

    @When("^I ask whether it's work time yet \"(\\d+)\"")
    public boolean When(int workhour) throws Throwable {
        return workhour > 7 && workhour < 17;

    }

    @Given("is it work time \"(\\d+)\"")
    public void is_it_work_time() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("create employee \\{string}\\{string}\\{string} \\{word}")
    public void create_employee(String firstname, String lastname, String job_description, Date birthdate) {
        // Write code here that turns the phrase above into concrete actions
        Employee employee = new Employee(firstname, lastname, job_description, birthdate);
    }

    @Given("create employee \\{string}\\{string}\\{string}\\{word}")
    public void create_employee() {
        // Write code here that turns the phrase above into concrete actions
        Employee employee = new Employee();
    }



}
