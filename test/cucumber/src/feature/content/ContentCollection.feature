Feature: Employee content collection
  An employee wants to add, edit, and delete a content collection

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a content collection, edits it, and deletes it
    Given the employee Test begins using the application
    And the user begins entering a new content collection
    And the user sets the content collection's content collection name to "CucumberCollection"
    And the user sets the content collection's default source name to "TEST0001B"
    And the user sets the content collection's description to "Customer Collection"
    And the user adds the new content collection
    Then no error should occur
    And the user begins specifying a content collection to edit
    And the user sets the content collection's content collection name to "CucumberCollection"
    And the user begins editing the content collection
    Then no error should occur
    And the user sets the content collection's description to "Edited Customer Collection"
    And the user finishes editing the content collection
    Then no error should occur
    And the user begins deleting a content collection
    And the user sets the content collection's content collection name to "CucumberCollection"
    And the user deletes the content collection
    Then no error should occur
