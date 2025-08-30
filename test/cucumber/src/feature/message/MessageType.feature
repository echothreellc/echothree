Feature: Employee message type
  An employee wants to add, edit, and delete a message type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an message type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new message type
    And the user sets the message type's component vendor to ECHO_THREE
    And the user sets the message type's entity type to Item
    And the user sets the message type's message type to CucumberTest
    And the user sets the message type's mime type usage type to TEXT
    And the user sets the message type's sort order to "1"
    And the user sets the message type's description to "Test Message Type"
    And the user adds the new message type
    Then no error should occur
    When the user begins specifying a message type to edit
    And the user sets the message type's component vendor to ECHO_THREE
    And the user sets the message type's entity type to Item
    And the user sets the message type's name to the last message type added
    When the user begins editing the message type
    Then no error should occur
    When the user sets the message type's description to "Test Edited Message Type"
    And the user finishes editing the message type
    Then no error should occur
    When the user begins deleting a message type
    And the user sets the message type's component vendor to ECHO_THREE
    And the user sets the message type's entity type to Item
    And the user sets the message type's name to the last message type added
    And the user deletes the message type
    Then no error should occur

  Scenario: Existing employee adds an message type to an entity type that is not extensible and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new message type
    And the user sets the message type's component vendor to ECHO_THREE
    And the user sets the message type's entity type to ItemAlias
    And the user sets the message type's message type to CucumberTest
    And the user sets the message type's mime type usage type to TEXT
    And the user sets the message type's sort order to "1"
    And the user sets the message type's description to "Test Message Type"
    And the user adds the new message type
    Then the execution error EntityTypeIsNotExtensible should occur
