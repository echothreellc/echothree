Feature: Employee entity type
  An employee wants to add, edit, and delete an entity type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an entity type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity type
    And the user sets the entity type's component vendor to ECHO_THREE
    And the user sets the entity type's entity type to TestEntityType
    And the user sets the entity type to keep all history
    And the user sets the entity type's lock timeout to "12"
    And the user sets the entity type's lock timeout unit of measure type name to "HOUR"
    And the user sets the entity type to be extensible
    And the user sets the entity type's sort order to "0"
    And the user sets the entity type's description to "Test Entity Type"
    And the user adds the new entity type
    Then no error should occur
    When the user begins specifying an entity type to edit
    And the user sets the entity type's component vendor to ECHO_THREE
    And the user sets the entity type's name to the last entity type added
    When the user begins editing the entity type
    Then no error should occur
    When the user sets the entity type's description to "Test Edited Entity Type"
    And the user finishes editing the entity type
    Then no error should occur
    When the user begins deleting an entity type
    And the user sets the entity type's component vendor to ECHO_THREE
    And the user sets the entity type's name to the last entity type added
    And the user deletes the entity type
    Then no error should occur
