Feature: Employee selector type
  An employee wants to add, edit, and delete a selector type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a selector type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new selector type
    And the user sets the selector type's selector kind name to ITEM
    And the user sets the selector type's name to TEST_SELECTOR_TYPE
    And the user sets the selector type's sort order to "1"
    And the user sets the selector type to be the default
    And the user sets the selector type's description to "Test Selector Type"
    And the user adds the new selector type
    Then no error should occur
    When the user begins specifying a selector type to edit
    And the user sets the selector type's selector kind name to ITEM
    And the user sets the selector type's name to the last selector type added
    When the user begins editing the selector type
    Then no error should occur
    And the user sets the selector type's sort order to "2"
    And the user sets the selector type's description to "Test Edited Selector Type"
    And the user finishes editing the selector type
    Then no error should occur
    When the user begins deleting a selector type
    And the user sets the selector type's selector kind name to ITEM
    And the user sets the selector type's name to the last selector type added
    And the user deletes the selector type
    Then no error should occur

  Scenario: Existing employee adds a selector type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new selector type
    And the user sets the selector type's selector kind name to ITEM
    And the user sets the selector type's name to OFFER
    And the user sets the selector type's sort order to "1"
    And the user sets the selector type to be the default
    And the user adds the new selector type
    Then the execution error DuplicateSelectorTypeName should occur
