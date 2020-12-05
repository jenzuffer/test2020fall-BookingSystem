Feature: Is it work time?
  You can create bookings within these employee times

  Scenario Outline: Current hour is work hour or not
    Given is it work time "<time of day start>" "<time of day end>" "<day of week>"
    When I ask whether it's work time yet
    Then I should be told "<answer>"

    Examples:
      | time of day start | time of day end | answer                                | day of week |
      | 08:00             | 16:00           | you can not create bookings currently | Sunday      |
      | 08:01             | 15:59           | you can create bookings currently     | not Sunday  |