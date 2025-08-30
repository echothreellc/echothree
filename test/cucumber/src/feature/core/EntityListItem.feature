Feature: Employee entity attribute
  An employee wants to add, edit, and delete an entity list item

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an entity list item, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to LISTITEM
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test List Item Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins entering a new entity list item
    And the user sets the entity list item's component vendor to ECHO_THREE
    And the user sets the entity list item's entity type to Item
    And the user sets the entity list item's entity attribute to the last entity attribute added
    And the user sets the entity list item's name to TEST_LIST_ITEM
    And the user sets the entity list item's sort order to "1"
    And the user sets the entity list item to be the default
    And the user sets the entity list item's description to "Test List Item"
    And the user adds the new entity list item
    Then no error should occur
    When the user begins specifying an entity list item to edit
    And the user sets the entity list item's component vendor to ECHO_THREE
    And the user sets the entity list item's entity type to Item
    And the user sets the entity list item's entity attribute to the last entity attribute added
    And the user sets the entity list item's name to the last entity list item added
    When the user begins editing the entity list item
    Then no error should occur
    And the user sets the entity list item's description to "Test Edited List Item"
    And the user finishes editing the entity list item
    Then no error should occur
    When the user begins deleting an entity list item
    And the user sets the entity list item's component vendor to ECHO_THREE
    And the user sets the entity list item's entity type to Item
    And the user sets the entity list item's entity attribute to the last entity attribute added
    And the user sets the entity list item's name to the last entity list item added
    And the user deletes the entity list item
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur
