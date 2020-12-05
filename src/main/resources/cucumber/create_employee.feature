


Feature: create Employee
  You can create an Employee

  Scenario Outline: You created an Employee
    Given create employee {string}{string}{string}{word}
    When i enter create_employee
    Then i should respond <status>
    Examples:
      | employee_created_status | status                             |
      | true                    | You succesfully created a employee |
      | false                   | You failed at creating an employee |