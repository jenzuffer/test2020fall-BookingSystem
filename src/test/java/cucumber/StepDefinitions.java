package cucumber;



import dto.Employee;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class StepDefinitions {
    private String today;
    private String ActualAnswer;

    private boolean Sunday;
    private boolean timeAllowed;
    public static SimpleDateFormat parser = new SimpleDateFormat("HH:mm");


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

    }

    @Given("create employee {string} {string} {string} {string}")
    public void create_employee(String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions
        try {
            Date date = new SimpleDateFormat("yyyy-dd-MM").parse(string4);
            Employee employee = new Employee(string, string2, string3, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    @When("i enter create_employee")
    public void i_enter_create_employee() {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("i should respond {string}")
    public void i_should_respond(String string) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("status: " + string);
    }

    @Given("is it work time {string} {string} {string}")
    public void is_it_work_time(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        try {
            Date date_start = parser.parse(string);
            Date date_end = parser.parse(string2);
            if (date_start.after(parser.parse("8:00")) && date_end.before(parser.parse("16:00")))
            {
                timeAllowed = true;
            }
            else{
                timeAllowed = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sunday = string3.equals("Sunday");

    }

    @When("I ask whether it's work time yet")
    public void i_ask_whether_it_s_work_time_yet() {
        // Write code here that turns the phrase above into concrete actions
        if (Sunday){
            ActualAnswer = "you can not create bookings currently";
        } else if (!timeAllowed) {
            ActualAnswer = "you can not create bookings currently";
        }
        else {
            ActualAnswer = "you can create bookings currently";
        }
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String string) {
        // Write code here that turns the phrase above into concrete actions
        Assertions.assertEquals(ActualAnswer, string);
    }

}
