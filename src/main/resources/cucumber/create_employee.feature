Feature: create Employee
  You can create an Employee

  Scenario Outline: You created an Employee
    Given create employee "<name>" "<last_name>" "<job_description>" "<birthdate>"
    When i enter create_employee
    Then i should respond "<status>"
    Examples:
      | status                             | name  | last_name | job_description | birthdate  |
      | You succesfully created a employee | john  | johnson   | a job           | 1990-20-11 |
      | You failed at creating an employee | peter | peterson  | b job           | 1990-20-11 |