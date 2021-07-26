Feature: Employee filter kind
  An employee wants to add, edit, and delete a filter kind

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a filter kind, edits it, and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new filter kind
    And the user sets the filter kind's name to TEST_FILTER_KIND
    And the user sets the filter kind's sort order to "1"
    And the user sets the filter kind to be the default
    And the user sets the filter kind's description to "Test Filter Kind"
    And the user adds the new filter kind
    Then no error should occur
    And the user begins specifying a filter kind to edit
    And the user sets the filter kind's name to the last filter kind added
    And the user begins editing the filter kind
    Then no error should occur
    And the user sets the filter kind's sort order to "2"
    And the user sets the filter kind's description to "Test Edited Filter Kind"
    And the user finishes editing the filter kind
    Then no error should occur
    And the user begins deleting a filter kind
    And the user sets the filter kind's name to the last filter kind added
    And the user deletes the filter kind
    Then no error should occur
