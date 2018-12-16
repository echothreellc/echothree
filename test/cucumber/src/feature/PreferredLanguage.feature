Feature: Preferred language
  A customer wants to set the preferred language

  Scenario: An anonymous customer sets their preferred language
    Given Test is not currently logged in
    When the customer Test sets their preferred language to "de"
    Then no error should occur
