Feature: Employee period type
  An employee wants to add, edit, and delete a period type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a period type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new period kind
    And the user sets the period kind's name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period kind's sort order to "1"
    And the user sets the period kind to "not be" the default
    And the user adds the new period kind
    Then no error should occur
    When the user begins entering a new period type
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to "TEST_PARENT_PERIOD_TYPE"
    And the user sets the period type's sort order to "1"
    And the user sets the period type to "not be" the default
    And the user adds the new period type
    Then no error should occur
    When the user begins entering a new period type
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to "TEST_OTHER_PARENT_PERIOD_TYPE"
    And the user sets the period type's sort order to "1"
    And the user sets the period type to "not be" the default
    And the user adds the new period type
    Then no error should occur
    When the user begins entering a new period type
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to "TEST_PERIOD_TYPE"
    And the user sets the period type's sort order to "1"
    And the user sets the period type to "be" the default
    And the user sets the period type's parent period type name to "TEST_PARENT_PERIOD_TYPE"
    And the user sets the period type's workflow name to "FISCAL_PERIOD_STATUS"
    And the user sets the period type's workflow entrance name to "NEW_FISCAL_PERIOD"
    And the user sets the period type's description to "Test Period Type"
    And the user adds the new period type
    Then no error should occur
    When the user begins specifying a period type to edit
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to the last period type added
    When the user begins editing the period type
    Then no error should occur
    And the user sets the period type's parent period type name to "TEST_OTHER_PARENT_PERIOD_TYPE"
    And the user sets the period type's sort order to "2"
    And the user sets the period type's description to "Test Edited Period Type"
    And the user finishes editing the period type
    Then no error should occur
    When the user begins specifying a period type to edit
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to the last period type added
    When the user begins editing the period type
    Then no error should occur
    And the period type's parent period type name should be "TEST_OTHER_PARENT_PERIOD_TYPE"
    And the user finishes editing the period type
    Then no error should occur
    When the user begins deleting a period type
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to the last period type added
    And the user deletes the period type
    Then no error should occur
    When the user begins deleting a period type
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to "TEST_PARENT_PERIOD_TYPE"
    And the user deletes the period type
    Then no error should occur
    When the user begins deleting a period type
    And the user sets the period type's period kind name to "TEST_PERIOD_TYPE_KIND"
    And the user sets the period type's name to "TEST_OTHER_PARENT_PERIOD_TYPE"
    And the user deletes the period type
    Then no error should occur
    When the user begins deleting a period kind
    And the user sets the period kind's name to "TEST_PERIOD_TYPE_KIND"
    And the user deletes the period kind
    Then no error should occur

  Scenario: Existing employee adds a period type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new period type
    And the user sets the period type's period kind name to "FISCAL"
    And the user sets the period type's name to "YEAR"
    And the user sets the period type's sort order to "1"
    And the user sets the period type to "be" the default
    And the user adds the new period type
    Then the execution error DuplicatePeriodTypeName should occur
