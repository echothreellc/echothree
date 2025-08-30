Feature: Employee warehouse
  An employee wants to add, edit, and delete a warehouse

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a warehouse, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new warehouse
    And the user sets the warehouse's warehouse name to "CucumberWarehouse"
    And the user sets the warehouse's warehouse type name to "DEFAULT"
    And the user sets the warehouse's name to "Cucumber Warehouse"
    And the user sets the warehouse to not be the default
    And the user sets the warehouse's sort order to "10"
    And the user sets the warehouse's inventory move printer group name to "DEFAULT"
    And the user sets the warehouse's picklist printer group mame to "DEFAULT"
    And the user sets the warehouse's packing list printer group name to "DEFAULT"
    And the user sets the warehouse's shipping manifest printer group name to "DEFAULT"
    And the user adds the new warehouse
    Then no error should occur
    When the user begins specifying a warehouse to edit
    And the user sets the warehouse's warehouse name to the last warehouse added
    When the user begins editing the warehouse
    Then no error should occur
    And the user sets the warehouse's name to "Edited Cucumber Warehouse"
    And the user finishes editing the warehouse
    Then no error should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur
