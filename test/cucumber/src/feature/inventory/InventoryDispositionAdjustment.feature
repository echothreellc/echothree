Feature: Employee inventory disposition adjustment
  An employee wants to add, edit, and delete an inventory disposition adjustment

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory disposition adjustment, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory disposition adjustment
    And the user sets the inventory disposition adjustment's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition adjustment's inventory disposition adjustment name to "CucumberInventoryDispositionAdjName"
    And the user sets the inventory disposition adjustment's inventory disposition name to "TEST_INVENTORY_DISPOSITION"
    And the user sets the inventory disposition adjustment's inventory adjustment type name to "SET"
    And the user sets the inventory disposition adjustment's inventory bucket type name to "TOTAL_SOH"
    And the user sets the inventory disposition adjustment to not be the default
    And the user sets the inventory disposition adjustment's sort order to "10"
    And the user sets the inventory disposition adjustment's description to "Cucumber Inventory Disposition Adjustment Name"
    And the user adds the new inventory disposition adjustment
    Then no error should occur
    When the user begins specifying an inventory disposition adjustment to edit
    And the user sets the inventory disposition adjustment's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition adjustment's inventory disposition name to "TEST_INVENTORY_DISPOSITION"
    And the user sets the inventory disposition adjustment's inventory disposition adjustment name to the last inventory disposition adjustment added
    When the user begins editing the inventory disposition adjustment
    Then no error should occur
    And the user sets the inventory disposition adjustment's sort order to "20"
    And the user sets the inventory disposition adjustment's description to "Edited Cucumber Inventory Disposition Adjustment Name"
    And the user finishes editing the inventory disposition adjustment
    Then no error should occur
    When the user begins deleting an inventory disposition adjustment
    And the user sets the inventory disposition adjustment's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition adjustment's inventory disposition name to "TEST_INVENTORY_DISPOSITION"
    And the user sets the inventory disposition adjustment's inventory disposition adjustment name to the last inventory disposition adjustment added
    And the user deletes the inventory disposition adjustment
    Then no error should occur

  Scenario: Existing employee adds an inventory disposition adjustment, edits it by entity ref, and then deletes it by entity ref
    Given the employee Test begins using the application
    When the user begins entering a new inventory disposition adjustment
    And the user sets the inventory disposition adjustment's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory disposition adjustment's inventory disposition name to "TEST_INVENTORY_DISPOSITION"
    And the user sets the inventory disposition adjustment's inventory adjustment type name to "SET"
    And the user sets the inventory disposition adjustment's inventory bucket type name to "TOTAL_SOH"
    And the user sets the inventory disposition adjustment's inventory disposition adjustment name to "CucumberInventoryDispositionAdjRef"
    And the user sets the inventory disposition adjustment to not be the default
    And the user sets the inventory disposition adjustment's sort order to "10"
    And the user sets the inventory disposition adjustment's description to "Cucumber Inventory Disposition Adjustment Ref"
    And the user adds the new inventory disposition adjustment
    Then no error should occur
    When the user begins specifying an inventory disposition adjustment to edit
    And the user sets the inventory disposition adjustment's entity ref to the last inventory disposition adjustment added
    When the user begins editing the inventory disposition adjustment
    Then no error should occur
    And the user sets the inventory disposition adjustment's sort order to "20"
    And the user sets the inventory disposition adjustment's description to "Edited Cucumber Inventory Disposition Adjustment Ref"
    And the user finishes editing the inventory disposition adjustment
    Then no error should occur
    When the user begins deleting an inventory disposition adjustment
    And the user sets the inventory disposition adjustment's entity ref to the last inventory disposition adjustment added
    And the user deletes the inventory disposition adjustment
    Then no error should occur
