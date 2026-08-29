Feature: Employee inventory transaction role type
  An employee wants to add, edit, and delete an inventory transaction role type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory transaction role type, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory transaction role type
    And the user sets the inventory transaction role type's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction role type's inventory transaction role type name to "CucumberInventoryTransactionRoleType"
    And the user sets the inventory transaction role type to not be the default
    And the user sets the inventory transaction role type's sort order to "10"
    And the user sets the inventory transaction role type's description to "Cucumber Inventory Transaction Role Type"
    And the user adds the new inventory transaction role type
    Then no error should occur
    When the user begins specifying an inventory transaction role type to edit
    And the user sets the inventory transaction role type's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction role type's inventory transaction role type name to the last inventory transaction role type added
    When the user begins editing the inventory transaction role type
    Then no error should occur
    And the user sets the inventory transaction role type's sort order to "20"
    And the user sets the inventory transaction role type's description to "Edited Cucumber Inventory Transaction Role Type"
    And the user finishes editing the inventory transaction role type
    Then no error should occur
    When the user begins deleting an inventory transaction role type
    And the user sets the inventory transaction role type's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction role type's inventory transaction role type name to the last inventory transaction role type added
    And the user deletes the inventory transaction role type
    Then no error should occur
