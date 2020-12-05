Feature: Is it work time?
  You can create bookings within these employee times

  Scenario Outline: Current hour is work hour or not
    Given is it work time {int}
    When I ask whether it's work time yet
    Then I should be told "<answer>"

    Examples:
      | hour_minutes            | answer |
      | true         | you can create bookings currently   |
      | false         | Nope   |
      | anything else! | Nope   |