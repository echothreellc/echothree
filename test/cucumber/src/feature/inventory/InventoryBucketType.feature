Feature: Employee inventory costing method
  An employee wants to add, edit, and delete an inventory costing method

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory costing method, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory costing method
    And the user sets the inventory costing method's inventory costing method name to "CucumberInventoryBucketType"
    And the user sets the inventory costing method to not be the default
    And the user sets the inventory costing method's sort order to "10"
    And the user sets the inventory costing method's description to "Cucumber Inventory Bucket Type"
    And the user adds the new inventory costing method
    Then no error should occur
    When the user begins specifying an inventory costing method to edit
    And the user sets the inventory costing method's inventory costing method name to the last inventory costing method added
    When the user begins editing the inventory costing method
    Then no error should occur
    And the user sets the inventory costing method's sort order to "20"
    And the user sets the inventory costing method's description to "Edited Cucumber Inventory Bucket Type"
    And the user finishes editing the inventory costing method
    Then no error should occur
    When the user begins deleting an inventory costing method
    And the user sets the inventory costing method's inventory costing method name to the last inventory costing method added
    And the user deletes the inventory costing method
    Then no error should occur
