Feature: Record user visit campaign
  A customer causes a campaign to be recorded for their user visit

  Scenario: An anonymous customer causes a campaign with only campaign set to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit campaign
    And the user sets the user visit campaign's campaign value to "Cucumber Campaign"
    When the user adds the new user visit campaign
    Then no error should occur

  Scenario: An anonymous customer causes a campaign with only campaign source set to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit campaign
    And the user sets the user visit campaign's campaign source value to "Cucumber Campaign Source"
    When the user adds the new user visit campaign
    Then no error should occur

  Scenario: An anonymous customer causes a campaign with only campaign medium set to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit campaign
    And the user sets the user visit campaign's campaign medium value to "Cucumber Campaign Medium"
    When the user adds the new user visit campaign
    Then no error should occur

  Scenario: An anonymous customer causes a campaign with only campaign term set to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit campaign
    And the user sets the user visit campaign's campaign term value to "Cucumber Campaign Term"
    When the user adds the new user visit campaign
    Then no error should occur

  Scenario: An anonymous customer causes a campaign with only campaign content set to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit campaign
    And the user sets the user visit campaign's campaign content value to "Cucumber Campaign Content"
    When the user adds the new user visit campaign
    Then no error should occur

  Scenario: An anonymous customer causes a campaign with all fields set to be recorded
    Given the customer Test begins using the application
    When the user begins entering a new user visit campaign
    And the user sets the user visit campaign's campaign value to "Cucumber Campaign"
    And the user sets the user visit campaign's campaign source value to "Cucumber Campaign Source"
    And the user sets the user visit campaign's campaign medium value to "Cucumber Campaign Medium"
    And the user sets the user visit campaign's campaign term value to "Cucumber Campaign Term"
    And the user sets the user visit campaign's campaign content value to "Cucumber Campaign Content"
    When the user adds the new user visit campaign
    Then no error should occur
