Feature: Employee inventory adjustment type
  An employee wants to add, edit, and delete an inventory adjustment type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory adjustment type, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory adjustment type
    And the user sets the inventory adjustment type's inventory adjustment type name to "CucumberInventoryAdjustmentType"
    And the user sets the inventory adjustment type to not be the default
    And the user sets the inventory adjustment type's sort order to "10"
    And the user sets the inventory adjustment type's description to "Cucumber Inventory Adjustment Type"
    And the user adds the new inventory adjustment type
    Then no error should occur
    When the user begins specifying an inventory adjustment type to edit
    And the user sets the inventory adjustment type's inventory adjustment type name to the last inventory adjustment type added
    When the user begins editing the inventory adjustment type
    Then no error should occur
    And the user sets the inventory adjustment type's sort order to "20"
    And the user sets the inventory adjustment type's description to "Edited Cucumber Inventory Adjustment Type"
    And the user finishes editing the inventory adjustment type
    Then no error should occur
    When the user begins deleting an inventory adjustment type
    And the user sets the inventory adjustment type's inventory adjustment type name to the last inventory adjustment type added
    And the user deletes the inventory adjustment type
    Then no error should occur
