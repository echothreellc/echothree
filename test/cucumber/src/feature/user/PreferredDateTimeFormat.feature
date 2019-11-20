Feature: Preferred date time format
  A customer wants to set the preferred date time format

  Scenario: An anonymous customer sets their preferred date time format
    Given the customer Test is not currently logged in
    When the customer Test sets their preferred date time format to "DEFAULT"
    Then no error should occur
