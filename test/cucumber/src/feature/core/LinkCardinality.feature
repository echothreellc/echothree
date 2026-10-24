Feature: Employee link cardinality
  An employee wants to add, edit, and delete a link cardinality

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a link cardinality, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new link cardinality
    And the user sets the link cardinality's link cardinality name to "CucumberLinkCardinality"
    And the user sets the link cardinality to not be the default
    And the user sets the link cardinality's sort order to "10"
    And the user sets the link cardinality's description to "Cucumber Link Cardinality"
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins specifying a link cardinality to edit
    And the user sets the link cardinality's link cardinality name to the last link cardinality added
    When the user begins editing the link cardinality
    Then no error should occur
    And the user sets the link cardinality's sort order to "20"
    And the user sets the link cardinality's description to "Edited Cucumber Link Cardinality"
    And the user finishes editing the link cardinality
    Then no error should occur
    When the user begins deleting a link cardinality
    And the user sets the link cardinality's link cardinality name to the last link cardinality added
    And the user deletes the link cardinality
    Then no error should occur
