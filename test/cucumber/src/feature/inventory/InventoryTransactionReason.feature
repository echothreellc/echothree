Feature: Employee inventory transaction reason
  An employee wants to add, edit, and delete an inventory transaction reason

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory transaction reason, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory transaction reason
    And the user sets the inventory transaction reason's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction reason's inventory transaction reason name to "CucumberInventoryTransactionReasonName"
    And the user sets the inventory transaction reason's inventory disposition name to "TEST_INVENTORY_DISPOSITION"
    And the user sets the inventory transaction reason to not be the default
    And the user sets the inventory transaction reason's sort order to "10"
    And the user sets the inventory transaction reason's description to "Cucumber Inventory Transaction Reason Name"
    And the user adds the new inventory transaction reason
    Then no error should occur
    When the user begins specifying an inventory transaction reason to edit
    And the user sets the inventory transaction reason's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction reason's inventory transaction reason name to the last inventory transaction reason added
    When the user begins editing the inventory transaction reason
    Then no error should occur
    And the user sets the inventory transaction reason's sort order to "20"
    And the user sets the inventory transaction reason's description to "Edited Cucumber Inventory Transaction Reason Name"
    And the user finishes editing the inventory transaction reason
    Then no error should occur
    When the user begins deleting an inventory transaction reason
    And the user sets the inventory transaction reason's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction reason's inventory transaction reason name to the last inventory transaction reason added
    And the user deletes the inventory transaction reason
    Then no error should occur

  Scenario: Existing employee adds an inventory transaction reason, edits it by entity ref, and then deletes it by entity ref
    Given the employee Test begins using the application
    When the user begins entering a new inventory transaction reason
    And the user sets the inventory transaction reason's inventory transaction type name to "TEST_INVENTORY_TRANSACTION_TYPE"
    And the user sets the inventory transaction reason's inventory disposition name to "TEST_INVENTORY_DISPOSITION"
    And the user sets the inventory transaction reason's inventory transaction reason name to "CucumberInventoryTransactionReasonRef"
    And the user sets the inventory transaction reason to not be the default
    And the user sets the inventory transaction reason's sort order to "10"
    And the user sets the inventory transaction reason's description to "Cucumber Inventory Transaction Reason Ref"
    And the user adds the new inventory transaction reason
    Then no error should occur
    When the user begins specifying an inventory transaction reason to edit
    And the user sets the inventory transaction reason's entity ref to the last inventory transaction reason added
    When the user begins editing the inventory transaction reason
    Then no error should occur
    And the user sets the inventory transaction reason's sort order to "20"
    And the user sets the inventory transaction reason's description to "Edited Cucumber Inventory Transaction Reason Ref"
    And the user finishes editing the inventory transaction reason
    Then no error should occur
    When the user begins deleting an inventory transaction reason
    And the user sets the inventory transaction reason's entity ref to the last inventory transaction reason added
    And the user deletes the inventory transaction reason
    Then no error should occur
