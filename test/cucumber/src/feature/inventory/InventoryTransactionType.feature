Feature: Employee inventory transaction type
  An employee wants to add, edit, and delete an inventory transaction type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an inventory transaction type, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to "CucumberInventoryTransactionTypeName"
    And the user sets the inventory transaction type to not be the default
    And the user sets the inventory transaction type's sort order to "10"
    And the user sets the inventory transaction type's description to "Cucumber Inventory Transaction Type Name"
    And the user adds the new inventory transaction type
    Then no error should occur
    When the user begins specifying an inventory transaction type to edit
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    When the user begins editing the inventory transaction type
    Then no error should occur
    And the user sets the inventory transaction type's sort order to "20"
    And the user sets the inventory transaction type's description to "Edited Cucumber Inventory Transaction Type Name"
    And the user finishes editing the inventory transaction type
    Then no error should occur
    When the user begins deleting an inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user deletes the inventory transaction type
    Then no error should occur

  Scenario: Existing employee adds an inventory transaction type, edits it by entity ref, and then deletes it by entity ref
    Given the employee Test begins using the application
    When the user begins entering a new inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to "CucumberInventoryTransactionTypeRef"
    And the user sets the inventory transaction type to not be the default
    And the user sets the inventory transaction type's sort order to "10"
    And the user sets the inventory transaction type's description to "Cucumber Inventory Transaction Type Ref"
    And the user adds the new inventory transaction type
    Then no error should occur
    When the user begins specifying an inventory transaction type to edit
    And the user sets the inventory transaction type's entity ref to the last inventory transaction added
    When the user begins editing the inventory transaction type
    Then no error should occur
    And the user sets the inventory transaction type's sort order to "20"
    And the user sets the inventory transaction type's description to "Edited Cucumber Inventory Transaction Type Ref"
    And the user finishes editing the inventory transaction type
    Then no error should occur
    When the user begins deleting an inventory transaction type
    And the user sets the inventory transaction type's entity ref to the last inventory transaction added
    And the user deletes the inventory transaction type
    Then no error should occur

  Scenario: Transfer transaction sequence survives edits independently of the transaction sequence
    When the user begins entering a new inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to "CucumberInventoryTransferSequence"
    And the user sets the inventory transaction type to not be the default
    And the user sets the inventory transaction type's sort order to "10"
    And the user sets the inventory transaction type's inventory transfer transaction sequence type name to "SALES_ORDER"
    And the user sets the inventory transaction type's inventory transaction sequence type name to "PURCHASE_ORDER"
    And the user adds the new inventory transaction type
    Then no error should occur
    And the inventory transaction type's inventory transfer transaction sequence type name should be "SALES_ORDER"
    And the inventory transaction type's inventory transaction sequence type name should be "PURCHASE_ORDER"
    When the user begins specifying an inventory transaction type to edit
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user begins editing the inventory transaction type
    Then no error should occur
    And the user sets the inventory transaction type's sort order to "20"
    And the user finishes editing the inventory transaction type
    Then no error should occur
    And the inventory transaction type's inventory transfer transaction sequence type name should be "SALES_ORDER"
    When the user begins specifying an inventory transaction type to edit
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user begins editing the inventory transaction type
    Then no error should occur
    And the user sets the inventory transaction type's inventory transfer transaction sequence type name to "PURCHASE_ORDER"
    And the user finishes editing the inventory transaction type
    Then no error should occur
    And the inventory transaction type's inventory transfer transaction sequence type name should be "PURCHASE_ORDER"
    When the user begins specifying an inventory transaction type to edit
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user begins editing the inventory transaction type
    Then no error should occur
    And the user sets the inventory transaction type's inventory transfer transaction sequence type name to ""
    And the user finishes editing the inventory transaction type
    Then no error should occur
    And the inventory transaction type's inventory transfer transaction sequence type name should be ""
    And the inventory transaction type's inventory transaction sequence type name should be "PURCHASE_ORDER"
    When the user begins deleting an inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user deletes the inventory transaction type
    Then no error should occur

  Scenario: Unknown transfer transaction sequence is rejected during creation
    When the user begins entering a new inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to "CucumberInventoryTransferSequenceInvalid"
    And the user sets the inventory transaction type to not be the default
    And the user sets the inventory transaction type's sort order to "10"
    And the user sets the inventory transaction type's inventory transfer transaction sequence type name to "CucumberMissingSequenceType"
    And the user adds the new inventory transaction type
    Then the execution error UnknownSequenceTypeName should occur

  Scenario: Unknown transfer transaction sequence is rejected during editing
    When the user begins entering a new inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to "CucumberInventoryTransferSequenceEdit"
    And the user sets the inventory transaction type to not be the default
    And the user sets the inventory transaction type's sort order to "10"
    And the user adds the new inventory transaction type
    Then no error should occur
    And the inventory transaction type's inventory transfer transaction sequence type name should be ""
    When the user begins specifying an inventory transaction type to edit
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user begins editing the inventory transaction type
    Then no error should occur
    And the user sets the inventory transaction type's inventory transfer transaction sequence type name to "CucumberMissingSequenceType"
    And the user finishes editing the inventory transaction type
    Then the execution error UnknownSequenceTypeName should occur
    And the user abandons editing the last inventory transaction type added
    Then no error should occur
    And the inventory transaction type's inventory transfer transaction sequence type name should be ""
    When the user begins deleting an inventory transaction type
    And the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added
    And the user deletes the inventory transaction type
    Then no error should occur
