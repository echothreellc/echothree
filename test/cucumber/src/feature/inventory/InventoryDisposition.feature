Feature: Employee inventory disposition
  An employee wants to add, edit, and delete an inventory disposition

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory disposition, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory disposition
    And the user sets the inventory disposition's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition's inventory disposition name to "CucumberInventoryDispositionName"
    And the user sets the inventory disposition to not be the default
    And the user sets the inventory disposition's sort order to "10"
    And the user sets the inventory disposition's description to "Cucumber Inventory Disposition Name"
    And the user adds the new inventory disposition
    Then no error should occur
    When the user begins specifying an inventory disposition to edit
    And the user sets the inventory disposition's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition's inventory disposition name to the last inventory disposition added
    When the user begins editing the inventory disposition
    Then no error should occur
    And the user sets the inventory disposition's sort order to "20"
    And the user sets the inventory disposition's description to "Edited Cucumber Inventory Disposition Name"
    And the user finishes editing the inventory disposition
    Then no error should occur
    When the user begins deleting an inventory disposition
    And the user sets the inventory disposition's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition's inventory disposition name to the last inventory disposition added
    And the user deletes the inventory disposition
    Then no error should occur

  Scenario: Existing employee adds an inventory disposition, edits it by entity ref, and then deletes it by entity ref
    Given the employee Test begins using the application
    When the user begins entering a new inventory disposition
    And the user sets the inventory disposition's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition's inventory disposition name to "CucumberInventoryDispositionRef"
    And the user sets the inventory disposition to not be the default
    And the user sets the inventory disposition's sort order to "10"
    And the user sets the inventory disposition's description to "Cucumber Inventory Disposition Ref"
    And the user adds the new inventory disposition
    Then no error should occur
    When the user begins specifying an inventory disposition to edit
    And the user sets the inventory disposition's entity ref to the last inventory disposition added
    When the user begins editing the inventory disposition
    Then no error should occur
    And the user sets the inventory disposition's sort order to "20"
    And the user sets the inventory disposition's description to "Edited Cucumber Inventory Disposition Ref"
    And the user finishes editing the inventory disposition
    Then no error should occur
    When the user begins deleting an inventory disposition
    And the user sets the inventory disposition's entity ref to the last inventory disposition added
    And the user deletes the inventory disposition
    Then no error should occur
