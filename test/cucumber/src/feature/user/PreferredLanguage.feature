Feature: Preferred language
  A customer wants to set the preferred language

  Scenario: An anonymous customer sets their preferred language
    Given the customer Test begins using the application
    And the user is not currently logged in
    When the user sets their preferred language to "de"
    Then no error should occur
