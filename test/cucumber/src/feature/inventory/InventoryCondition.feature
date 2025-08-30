Feature: Employee inventory condition
  An employee wants to add, edit, and delete an inventory condition

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory condition, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory condition
    And the user sets the inventory condition's inventory condition name to "CucumberInventoryCondition"
    And the user sets the inventory condition to be the default
    And the user sets the inventory condition's sort order to "10"
    And the user sets the inventory condition's description to "Cucumber Inventory Condition"
    And the user adds the new inventory condition
    Then no error should occur
    When the user begins specifying an inventory condition to edit
    And the user sets the inventory condition's inventory condition name to the last inventory condition added
    When the user begins editing the inventory condition
    Then no error should occur
    And the user sets the inventory condition's sort order to "20"
    And the user sets the inventory condition's description to "Edited Cucumber Inventory Condition"
    And the user finishes editing the inventory condition
    Then no error should occur
    When the user begins deleting an inventory condition
    And the user sets the inventory condition's inventory condition name to the last inventory condition added
    And the user deletes the inventory condition
    Then no error should occur
