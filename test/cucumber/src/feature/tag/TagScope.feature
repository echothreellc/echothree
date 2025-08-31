Feature: Employee tag scope
  An employee wants to add, edit, and delete a tag scope

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a tag scope with a name and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user sets the tag scope to not be the default
    And the user sets the tag scope's sort order to "10"
    And the user sets the tag scope's description to "Cucumber Tag Scope"
    And the user adds the new tag scope
    Then no error should occur
    When the user begins deleting a tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user deletes the tag scope
    Then no error should occur

  Scenario: Existing employee adds a tag scope without a name and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new tag scope
    And the user sets the tag scope to not be the default
    And the user sets the tag scope's sort order to "10"
    And the user sets the tag scope's description to "Cucumber Tag Scope"
    And the user adds the new tag scope
    Then no error should occur
    When the user begins deleting a tag scope
    And the user sets the tag scope's tag scope name to the last tag scope added
    And the user deletes the tag scope
    Then no error should occur

  Scenario: Existing employee adds a tag scope, edits it, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user sets the tag scope to not be the default
    And the user sets the tag scope's sort order to "10"
    And the user sets the tag scope's description to "Cucumber Tag Scope"
    And the user adds the new tag scope
    Then no error should occur
    When the user begins specifying a tag scope to edit
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    When the user begins editing the tag scope
    Then no error should occur
    When the user sets the tag scope's new tag scope name to "CUCUMBER_TAG_SCOPE_EDITED"
    And the user sets the tag scope's sort order to "20"
    And the user sets the tag scope's description to "Cucumber Tag Scope Edited"
    And the user finishes editing the tag scope
    Then no error should occur
    When the user begins deleting a tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE_EDITED"
    And the user deletes the tag scope
    Then no error should occur
