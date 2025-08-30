Feature: Employee inventory location group
  An employee wants to add, edit, and delete an inventory location group

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory location group, edits it, changes its status, and then deletes it
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
    Then no error should occur
    When the user begins specifying an inventory location group to edit
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to the last inventory location group added
    When the user begins editing the inventory location group
    Then no error should occur
    And the user sets the inventory location group's description to "Edited Cucumber Inventory Location Group"
    And the user finishes editing the inventory location group
    Then no error should occur
    When the user begins setting the status of an inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to the last inventory location group added
    And the user sets the inventory location group's status to "ACTIVE_TO_INVENTORY_PREP"
    And the user sets the status of the inventory location group
    Then no error should occur
    When the user begins deleting an inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to the last inventory location group added
    And the user deletes the inventory location group
    Then no error should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur
