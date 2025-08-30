Feature: Employee tag
  An employee wants to add, edit, and delete a tag

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a tag and deletes it
    Given the employee Test begins using the application
    Given the employee Test begins using the application
    When the user begins entering a new tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user sets the tag scope to not be the default
    And the user sets the tag scope's sort order to "10"
    And the user sets the tag scope's description to "Cucumber Tag Scope"
    And the user adds the new tag scope
    Then no error should occur
    When the user begins entering a new tag
    And the user sets the tag's tag scope name to the last tag scope added
    And the user sets the tag's tag name to "CUCUMBER_TAG"
    And the user adds the new tag
    Then no error should occur
    When the user begins deleting a tag
    And the user sets the tag's tag scope name to the last tag scope added
    And the user sets the tag's tag name to the last tag added
    And the user deletes the tag
    Then no error should occur
    When the user begins deleting a tag scope
    And the user sets the tag scope's tag scope name to the last tag scope added
    And the user deletes the tag scope
    Then no error should occur

  Scenario: Existing employee adds a tag, edits it, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new tag scope
    And the user sets the tag scope's tag scope name to "CUCUMBER_TAG_SCOPE"
    And the user sets the tag scope to not be the default
    And the user sets the tag scope's sort order to "10"
    And the user sets the tag scope's description to "Cucumber Tag Scope"
    And the user adds the new tag scope
    Then no error should occur
    When the user begins entering a new tag
    And the user sets the tag's tag scope name to the last tag scope added
    And the user sets the tag's tag name to "CUCUMBER_TAG"
    And the user adds the new tag
    Then no error should occur
    When the user begins specifying a tag to edit
    And the user sets the tag's tag scope name to the last tag scope added
    And the user sets the tag's tag name to "CUCUMBER_TAG"
    When the user begins editing the tag
    Then no error should occur
    When the user sets the tag's new tag name to "CUCUMBER_TAG_EDITED"
    And the user finishes editing the tag
    Then no error should occur
    When the user begins deleting a tag
    And the user sets the tag's tag scope name to the last tag scope added
    And the user sets the tag's tag name to "CUCUMBER_TAG_EDITED"
    And the user deletes the tag
    Then no error should occur
    When the user begins deleting a tag scope
    And the user sets the tag scope's tag scope name to the last tag scope added
    And the user deletes the tag scope
    Then no error should occur
