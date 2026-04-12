Feature: Employee filter adjustment
  An employee wants to add, edit, and delete a filter adjustment

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a filter adjustment, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new filter adjustment
    And the user sets the filter adjustment's filter kind name to PRICE
    And the user sets the filter adjustment's name to TEST_ADJUSTMENT_TYPE
    And the user sets the filter adjustment's sort order to "1"
    And the user sets the filter adjustment's filter adjustment source name to ITEM_PRICE
    And the user sets the filter adjustment's filter adjustment type name to PERCENT
    And the user sets the filter adjustment to be the default
    And the user sets the filter adjustment's description to "Test Filter Adjustment"
    And the user adds the new filter adjustment
    Then no error should occur
    When the user begins specifying a filter adjustment to edit
    And the user sets the filter adjustment's filter kind name to PRICE
    And the user sets the filter adjustment's name to the last filter adjustment added
    When the user begins editing the filter adjustment
    Then no error should occur
    And the user sets the filter adjustment's sort order to "2"
    And the user sets the filter adjustment's description to "Test Edited Filter Adjustment"
    And the user finishes editing the filter adjustment
    Then no error should occur
    When the user begins deleting a filter adjustment
    And the user sets the filter adjustment's filter kind name to PRICE
    And the user sets the filter adjustment's name to the last filter adjustment added
    And the user deletes the filter adjustment
    Then no error should occur

  Scenario: Existing employee adds a filter adjustment with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new filter adjustment
    And the user sets the filter adjustment's filter kind name to PRICE
    And the user sets the filter adjustment's name to EXAMPLE_5_PERCENT
    And the user sets the filter adjustment's filter adjustment source name to ITEM_PRICE
    And the user sets the filter adjustment's filter adjustment type name to PERCENT
    And the user sets the filter adjustment's sort order to "1"
    And the user sets the filter adjustment to be the default
    And the user adds the new filter adjustment
    Then the execution error DuplicateFilterAdjustmentName should occur
