Feature: Preferred time zone
  A customer wants to set the preferred time zone

  Scenario: An anonymous customer sets their preferred time zone
    Given Test is not currently logged in
    When the customer Test sets their preferred time zone to "US/Pacific"
    Then no error should occur
