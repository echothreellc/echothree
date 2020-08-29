Feature: Employee use type
  An employee wants to add, edit, and delete an use type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an use type, edits it, and deletes it
    Given the employee Test begins using the application
    And the user begins entering a new use type
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user sets the use type to not be the default
    And the user sets the use type's sort order to "10"
    And the user sets the use type's description to "Cucumber Use Type"
    And the user adds the new use type
    Then no error should occur
    And the user begins specifying an use type to edit
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user begins editing the use type
    Then no error should occur
    And the user sets the use type's sort order to "20"
    And the user finishes editing the use type
    Then no error should occur
    And the user begins deleting an use type
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user deletes the use type
    Then no error should occur
