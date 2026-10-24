Feature: Employee link type
  An employee wants to add, edit, and delete a link type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a link type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new link type
    And the user sets the link type's link type name to "CucumberLinkType"
    And the user sets the link type's source component vendor name to "ECHO_THREE"
    And the user sets the link type's source entity type name to "Item"
    And the user sets the link type's target component vendor name to "ECHO_THREE"
    And the user sets the link type's target entity type name to "Item"
    And the user sets the link type's link cardinality name to "MANY_TO_MANY"
    And the user sets the link type's link directionality name to "DIRECTED"
    And the user sets the link type's is transitive to "false"
    And the user sets the link type's guidance MIME type name to "text/plain"
    And the user sets the link type's guidance to "Use this relationship for the Cucumber test."
    And the user sets the link type's sort order to "10"
    And the user sets the link type's description to "Cucumber Link Type"
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to the last link type added
    When the user begins editing the link type
    Then no error should occur
    And the user sets the link type's sort order to "20"
    And the user sets the link type's description to "Edited Cucumber Link Type"
    And the user finishes editing the link type
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to the last link type added
    And the user deletes the link type
    Then no error should occur
