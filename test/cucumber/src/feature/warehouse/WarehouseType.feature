Feature: Employee warehouse type
  An employee wants to add, edit, and delete a warehouse type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a warehouse type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new warehouse type
    And the user sets the warehouse type's name to TEST_WAREHOUSE_TYPE
    And the user sets the warehouse type's priority to "1"
    And the user sets the warehouse type's sort order to "1"
    And the user sets the warehouse type to be the default
    And the user sets the warehouse type's description to "Test Warehouse Type"
    And the user adds the new warehouse type
    Then no error should occur
    When the user begins specifying a warehouse type to edit
    And the user sets the warehouse type's name to the last warehouse type added
    When the user begins editing the warehouse type
    Then no error should occur
    And the user sets the warehouse type's sort order to "2"
    And the user sets the warehouse type's description to "Test Edited Warehouse Type"
    And the user finishes editing the warehouse type
    Then no error should occur
    When the user begins deleting a warehouse type
    And the user sets the warehouse type's name to the last warehouse type added
    And the user deletes the warehouse type
    Then no error should occur

  Scenario: Existing employee adds a warehouse type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new warehouse type
    And the user sets the warehouse type's name to DEFAULT
    And the user sets the warehouse type's priority to "1"
    And the user sets the warehouse type's sort order to "1"
    And the user sets the warehouse type to be the default
    And the user adds the new warehouse type
    Then the execution error DuplicateWarehouseTypeName should occur
