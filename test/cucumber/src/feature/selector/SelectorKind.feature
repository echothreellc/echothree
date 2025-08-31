Feature: Employee selector kind
  An employee wants to add, edit, and delete a selector kind

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a selector kind, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new selector kind
    And the user sets the selector kind's name to TEST_SELECTOR_KIND
    And the user sets the selector kind's sort order to "1"
    And the user sets the selector kind to be the default
    And the user sets the selector kind's description to "Test Selector Kind"
    And the user adds the new selector kind
    Then no error should occur
    When the user begins specifying a selector kind to edit
    And the user sets the selector kind's name to the last selector kind added
    When the user begins editing the selector kind
    Then no error should occur
    And the user sets the selector kind's sort order to "2"
    And the user sets the selector kind's description to "Test Edited Selector Kind"
    And the user finishes editing the selector kind
    Then no error should occur
    When the user begins deleting a selector kind
    And the user sets the selector kind's name to the last selector kind added
    And the user deletes the selector kind
    Then no error should occur

  Scenario: Existing employee adds a selector kind with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new selector kind
    And the user sets the selector kind's name to ITEM
    And the user sets the selector kind's sort order to "1"
    And the user sets the selector kind to be the default
    And the user adds the new selector kind
    Then the execution error DuplicateSelectorKindName should occur
