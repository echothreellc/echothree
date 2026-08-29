Feature: Employee inventory transaction time type
  An employee wants to add, edit, and delete an inventory transaction time type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory transaction time type, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory transaction time type
    And the user sets the inventory transaction time type's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction time type's inventory transaction time type name to "CucumberInventoryTransactionTimeType"
    And the user sets the inventory transaction time type to not be the default
    And the user sets the inventory transaction time type's sort order to "10"
    And the user sets the inventory transaction time type's description to "Cucumber Inventory Transaction Time Type"
    And the user adds the new inventory transaction time type
    Then no error should occur
    When the user begins specifying an inventory transaction time type to edit
    And the user sets the inventory transaction time type's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction time type's inventory transaction time type name to the last inventory transaction time type added
    When the user begins editing the inventory transaction time type
    Then no error should occur
    And the user sets the inventory transaction time type's sort order to "20"
    And the user sets the inventory transaction time type's description to "Edited Cucumber Inventory Transaction Time Type"
    And the user finishes editing the inventory transaction time type
    Then no error should occur
    When the user begins deleting an inventory transaction time type
    And the user sets the inventory transaction time type's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction time type's inventory transaction time type name to the last inventory transaction time type added
    And the user deletes the inventory transaction time type
    Then no error should occur
