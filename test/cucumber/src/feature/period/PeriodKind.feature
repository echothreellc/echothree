Feature: Employee period kind
  An employee wants to add, edit, and delete a period kind

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a period kind, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new period kind
    And the user sets the period kind's name to TEST_PERIOD_KIND
    And the user sets the period kind's sort order to "1"
    And the user sets the period kind to be the default
    And the user sets the period kind's workflow name to FISCAL_PERIOD_STATUS
    And the user sets the period kind's workflow entrance name to NEW_FISCAL_PERIOD
    And the user sets the period kind's description to "Test Period Kind"
    And the user adds the new period kind
    Then no error should occur
    When the user begins specifying a period kind to edit
    And the user sets the period kind's name to the last period kind added
    When the user begins editing the period kind
    Then no error should occur
    And the user sets the period kind's sort order to "2"
    And the user sets the period kind's description to "Test Edited Period Kind"
    And the user finishes editing the period kind
    Then no error should occur
    When the user begins deleting a period kind
    And the user sets the period kind's name to the last period kind added
    And the user deletes the period kind
    Then no error should occur

  Scenario: Existing employee adds a period kind with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new period kind
    And the user sets the period kind's name to FISCAL
    And the user sets the period kind's sort order to "1"
    And the user sets the period kind to be the default
    And the user adds the new period kind
    Then the execution error DuplicatePeriodKindName should occur
