Feature: Employee content category
  An employee wants to add, edit, and delete a content category

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a content category, edits it, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new content collection
    And the user sets the content collection's content collection name to "CucumberCollection"
    And the user sets the content collection's default source name to "TEST0001B"
    And the user sets the content collection's description to "Cucumber Collection"
    And the user adds the new content collection
    Then no error should occur
    When the user begins entering a new content catalog
    And the user sets the content catalog's content collection name to "CucumberCollection"
    And the user sets the content catalog's content catalog name to "CucumberCatalog"
    And the user sets the content catalog's default source name to "TEST0001B"
    And the user sets the content catalog's sort order to "1"
    And the user sets the content catalog to be the default
    And the user sets the content catalog's description to "Cucumber Catalog"
    And the user adds the new content catalog
    Then no error should occur
    When the user begins entering a new content category
    And the user sets the content category's content collection name to "CucumberCollection"
    And the user sets the content category's content catalog name to "CucumberCatalog"
    And the user sets the content category's content category name to "CucumberCategory"
    And the user sets the content category's default source name to "TEST0001B"
    And the user sets the content category's sort order to "1"
    And the user sets the content category to be the default
    And the user sets the content category's description to "Cucumber Category"
    And the user adds the new content category
    Then no error should occur
    When the user begins specifying a content category to edit
    And the user sets the content category's content collection name to "CucumberCollection"
    And the user sets the content category's content catalog name to "CucumberCatalog"
    And the user sets the content category's content category name to "CucumberCategory"
    When the user begins editing the content category
    Then no error should occur
    And the user sets the content category's description to "Edited Cucumber Category"
    And the user finishes editing the content category
    Then no error should occur
    When the user begins deleting a content category
    And the user sets the content category's content collection name to "CucumberCollection"
    And the user sets the content category's content catalog name to "CucumberCatalog"
    And the user sets the content category's content category name to "CucumberCategory"
    And the user deletes the content category
    Then no error should occur
    When the user begins deleting a content catalog
    And the user sets the content catalog's content collection name to "CucumberCollection"
    And the user sets the content catalog's content catalog name to "CucumberCatalog"
    And the user deletes the content catalog
    Then no error should occur
    When the user begins deleting a content collection
    And the user sets the content collection's content collection name to "CucumberCollection"
    And the user deletes the content collection
    Then no error should occur
