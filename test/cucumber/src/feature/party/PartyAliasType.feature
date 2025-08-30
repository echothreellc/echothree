Feature: Employee party alias type
  An employee wants to add, edit, and delete a party alias type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a party alias type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new party alias type
    And the user sets the party alias type's party type to CUSTOMER
    And the user sets the party alias type's name to TEST_PARTY_ALIAS_TYPE
    And the user sets the party alias type's sort order to "1"
    And the user sets the party alias type to be the default
    And the user sets the party alias type's description to "Test Party Alias Type"
    And the user adds the new party alias type
    Then no error should occur
    When the user begins specifying a party alias type to edit
    And the user sets the party alias type's party type to CUSTOMER
    And the user sets the party alias type's name to the last party alias type added
    When the user begins editing the party alias type
    Then no error should occur
    And the user sets the party alias type's sort order to "2"
    And the user sets the party alias type's description to "Test Edited Party Alias Type"
    And the user finishes editing the party alias type
    Then no error should occur
    When the user begins deleting a party alias type
    And the user sets the party alias type's party type to CUSTOMER
    And the user sets the party alias type's name to the last party alias type added
    And the user deletes the party alias type
    Then no error should occur

  Scenario: Existing employee adds a party alias type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new party alias type
    And the user sets the party alias type's party type to CUSTOMER
    And the user sets the party alias type's name to TEST
    And the user sets the party alias type's sort order to "1"
    And the user sets the party alias type to be the default
    And the user adds the new party alias type
    Then the execution error DuplicatePartyAliasTypeName should occur
