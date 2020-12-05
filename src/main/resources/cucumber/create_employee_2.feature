Feature: create Employee
  You can create an Employee

  Scenario: create an employee

    Given i am creating an employee
    When i ask for parameters
    Then it should have following features:
      | id              |
      | firstname       |
      | lastname        |
      | job description |
      | birthdate       |