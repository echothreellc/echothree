Feature: Employee entity alias type
  An employee wants to add and delete an entity alias type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an entity alias type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity alias type
    And the user sets the entity alias type's component vendor to "ECHO_THREE"
    And the user sets the entity alias type's entity type to "Item"
    And the user sets the entity alias type's name to "TestAliasType"
    And the user sets the entity alias type to be the default
    And the user sets the entity alias type's sort order to "1"
    And the user sets the entity alias type's description to "Test Alias Type"
    And the user adds the new entity alias type
    Then no error should occur
    When the user begins specifying an entity alias type to edit
    And the user sets the entity alias type's component vendor to "ECHO_THREE"
    And the user sets the entity alias type's entity type to "Item"
    And the user sets the entity alias type's name to the last entity alias type added
    When the user begins editing the entity alias type
    Then no error should occur
    And the user sets the entity alias type's sort order to "10"
    And the user sets the entity alias type's description to "Test Edited Alias Type"
    And the user finishes editing the entity alias type
    Then no error should occur
    When the user begins deleting an entity alias type
    And the user sets the entity alias type's component vendor to "ECHO_THREE"
    And the user sets the entity alias type's entity type to "Item"
    And the user sets the entity alias type's name to the last entity alias type added
    And the user deletes the entity alias type
    Then no error should occur

  Scenario: Existing employee adds an entity alias type to an entity type that is not extensible and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new entity alias type
    And the user sets the entity alias type's component vendor to "ECHO_THREE"
    And the user sets the entity alias type's entity type to "ItemAlias"
    And the user sets the entity alias type's name to "TestAliasType"
    And the user sets the entity alias type to be the default
    And the user sets the entity alias type's sort order to "1"
    And the user sets the entity alias type's description to "Test Alias Type"
    And the user adds the new entity alias type
    Then the execution error EntityTypeIsNotExtensible should occur
