Feature: Employee tag scope
  An employee wants to add and delete a tag scope entity type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a tag scope entity type and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user sets the tag scope to not be the default
    And the user sets the tag scope's sort order to "10"
    And the user sets the tag scope's description to "Cucumber Tag Scope"
    And the user adds the new tag scope
    Then no error should occur
    When the user begins entering a new tag scope entity type
    And the user sets the tag scope entity type's tag scope name to the last tag scope added
    And the user sets the tag scope entity type's component vendor name to "ECHO_THREE"
    And the user sets the tag scope entity type's entity type name to "ITEM"
    And the user adds the new tag scope entity type
    Then no error should occur
    When the user begins deleting a tag scope entity type
    And the user sets the tag scope entity type's tag scope name to the last tag scope added
    And the user sets the tag scope entity type's component vendor name to "ECHO_THREE"
    And the user sets the tag scope entity type's entity type name to "ITEM"
    And the user deletes the tag scope entity type
    Then no error should occur
    When the user begins deleting a tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user deletes the tag scope
    Then no error should occur
    