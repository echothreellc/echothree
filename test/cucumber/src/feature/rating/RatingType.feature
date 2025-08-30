Feature: Employee rating type
  An employee wants to add, edit, and delete a rating type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an rating type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new rating type
    And the user sets the rating type's component vendor to ECHO_THREE
    And the user sets the rating type's entity type to Item
    And the user sets the rating type's rating type to CucumberTest
    And the user sets the rating type's sort order to "1"
    And the user sets the rating type's description to "Test Rating Type"
    And the user adds the new rating type
    Then no error should occur
    When the user begins specifying a rating type to edit
    And the user sets the rating type's component vendor to ECHO_THREE
    And the user sets the rating type's entity type to Item
    And the user sets the rating type's name to the last rating type added
    When the user begins editing the rating type
    Then no error should occur
    When the user sets the rating type's description to "Test Edited Rating Type"
    And the user finishes editing the rating type
    Then no error should occur
    When the user begins deleting a rating type
    And the user sets the rating type's component vendor to ECHO_THREE
    And the user sets the rating type's entity type to Item
    And the user sets the rating type's name to the last rating type added
    And the user deletes the rating type
    Then no error should occur

  Scenario: Existing employee adds an rating type to an entity type that is not extensible and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new rating type
    And the user sets the rating type's component vendor to ECHO_THREE
    And the user sets the rating type's entity type to ItemAlias
    And the user sets the rating type's rating type to CucumberTest
    And the user sets the rating type's sort order to "1"
    And the user sets the rating type's description to "Test Rating Type"
    And the user adds the new rating type
    Then the execution error EntityTypeIsNotExtensible should occur
