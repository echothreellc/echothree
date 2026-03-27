Feature: Employee filter kind
  An employee wants to add, edit, and delete a filter kind

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a filter kind, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new filter kind
    And the user sets the filter kind's name to TEST_FILTER_KIND
    And the user sets the filter kind's sort order to "1"
    And the user sets the filter kind to be the default
    And the user sets the filter kind's description to "Test Filter Kind"
    And the user adds the new filter kind
    Then no error should occur
    When the user begins specifying a filter kind to edit
    And the user sets the filter kind's name to the last filter kind added
    When the user begins editing the filter kind
    Then no error should occur
    And the user sets the filter kind's sort order to "2"
    And the user sets the filter kind's description to "Test Edited Filter Kind"
    And the user finishes editing the filter kind
    Then no error should occur
    When the user begins deleting a filter kind
    And the user sets the filter kind's name to the last filter kind added
    And the user deletes the filter kind
    Then no error should occur

  Scenario: Existing employee adds a filter kind with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new filter kind
    And the user sets the filter kind's name to PRICE
    And the user sets the filter kind's sort order to "1"
    And the user sets the filter kind to be the default
    And the user adds the new filter kind
    Then the execution error DuplicateFilterKindName should occur

  Scenario: Existing employee adds a filter kind, filter type, and attempts deleting the filter kind while in-use
    Given the employee Test begins using the application
    When the user begins entering a new filter kind
    And the user sets the filter kind's name to TEST_FILTER_KIND
    And the user sets the filter kind's sort order to "1"
    And the user sets the filter kind to be the default
    And the user sets the filter kind's description to "Test Filter Kind"
    And the user adds the new filter kind
    Then no error should occur
    When the user begins entering a new filter type
    And the user sets the filter type's filter kind name to TEST_FILTER_KIND
    And the user sets the filter type's name to TEST_FILTER_TYPE
    And the user sets the filter type's sort order to "1"
    And the user sets the filter type to be the default
    And the user sets the filter type's description to "Test Filter Type"
    And the user adds the new filter type
    Then no error should occur
    When the user begins deleting a filter kind
    And the user sets the filter kind's name to the last filter kind added
    And the user deletes the filter kind
    Then the execution error CannotDeleteFilterKindInUse should occur
    When the user begins deleting a filter type
    And the user sets the filter type's filter kind name to TEST_FILTER_KIND
    And the user sets the filter type's name to the last filter type added
    And the user deletes the filter type
    Then no error should occur
    When the user begins deleting a filter kind
    And the user sets the filter kind's name to the last filter kind added
    And the user deletes the filter kind
    Then no error should occur
