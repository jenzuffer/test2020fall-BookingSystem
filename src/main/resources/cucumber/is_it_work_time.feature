Feature: Is it work time?
  You can create bookings within these employee times

  Scenario Outline: Current hour is work hour or not
    Given is it work time <worktime>
    When I ask whether it's work time yet
    Then I should be told "<answer>"

    Examples:
      | worktime            | answer |
      | true         | you can create bookings currently   |
      | false         | Nope   |
      | anything else! | Nope   |