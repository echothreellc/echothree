Feature: Employee comment type
  An employee wants to add, edit, and delete a comment type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an comment type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new comment type
    And the user sets the comment type's component vendor to ECHO_THREE
    And the user sets the comment type's entity type to Item
    And the user sets the comment type's comment type to CucumberTest
    And the user sets the comment type's mime type usage type to TEXT
    And the user sets the comment type's sort order to "1"
    And the user sets the comment type's description to "Test Comment Type"
    And the user adds the new comment type
    Then no error should occur
    When the user begins specifying a comment type to edit
    And the user sets the comment type's component vendor to ECHO_THREE
    And the user sets the comment type's entity type to Item
    And the user sets the comment type's name to the last comment type added
    When the user begins editing the comment type
    Then no error should occur
    When the user sets the comment type's description to "Test Edited Comment Type"
    And the user finishes editing the comment type
    Then no error should occur
    When the user begins deleting a comment type
    And the user sets the comment type's component vendor to ECHO_THREE
    And the user sets the comment type's entity type to Item
    And the user sets the comment type's name to the last comment type added
    And the user deletes the comment type
    Then no error should occur

  Scenario: Existing employee adds an comment type to an entity type that is not extensible and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new comment type
    And the user sets the comment type's component vendor to ECHO_THREE
    And the user sets the comment type's entity type to ItemAlias
    And the user sets the comment type's comment type to CucumberTest
    And the user sets the comment type's mime type usage type to TEXT
    And the user sets the comment type's sort order to "1"
    And the user sets the comment type's description to "Test Comment Type"
    And the user adds the new comment type
    Then the execution error EntityTypeIsNotExtensible should occur
