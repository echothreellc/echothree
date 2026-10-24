Feature: Employee link directionality
  An employee wants to add, edit, and delete a link directionality

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a link directionality, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new link directionality
    And the user sets the link directionality's link directionality name to "CucumberLinkDirectionality"
    And the user sets the link directionality to not be the default
    And the user sets the link directionality's sort order to "10"
    And the user sets the link directionality's description to "Cucumber Link Directionality"
    And the user adds the new link directionality
    Then no error should occur
    When the user begins specifying a link directionality to edit
    And the user sets the link directionality's link directionality name to the last link directionality added
    When the user begins editing the link directionality
    Then no error should occur
    And the user sets the link directionality's sort order to "20"
    And the user sets the link directionality's description to "Edited Cucumber Link Directionality"
    And the user finishes editing the link directionality
    Then no error should occur
    When the user begins deleting a link directionality
    And the user sets the link directionality's link directionality name to the last link directionality added
    And the user deletes the link directionality
    Then no error should occur
