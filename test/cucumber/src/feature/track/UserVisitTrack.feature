Feature: Record user visit track
  A customer causes a track to be recorded for their user visit

  Scenario: An anonymous customer causes a track to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit track
    And the user sets the user visit track's track value to "Cucumber Track"
    When the user adds the new user visit track
    Then no error should occur
