Feature: Employee entity attribute
  An employee wants to add, edit, and delete an entity attribute group

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an entity attribute group, edits it, and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new entity attribute group
    And the user sets the entity attribute group's name to TEST_ENTITY_ATTRIBUTE_GROUP
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to be the default
    And the user sets the entity attribute group's description to "Test Entity Attribute Group"
    And the user adds the new entity attribute group
    Then no error should occur
    And the user begins specifying an entity attribute group to edit
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user begins editing the entity attribute group
    Then no error should occur
    And the user sets the entity attribute group's sort order to "2"
    And the user sets the entity attribute group's description to "Test Edited Entity Attribute Group"
    And the user finishes editing the entity attribute group
    Then no error should occur
    And the user begins deleting an entity attribute group
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user deletes the entity attribute group
    Then no error should occur

  Scenario: Existing employee adds an entity attribute group without a name, edits it, and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new entity attribute group
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to be the default
    And the user sets the entity attribute group's description to "Test Entity Attribute Group"
    And the user adds the new entity attribute group
    Then no error should occur
    And the user begins specifying an entity attribute group to edit
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user begins editing the entity attribute group
    Then no error should occur
    And the user sets the entity attribute group's sort order to "2"
    And the user sets the entity attribute group's description to "Test Edited Entity Attribute Group"
    And the user finishes editing the entity attribute group
    Then no error should occur
    And the user begins deleting an entity attribute group
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user deletes the entity attribute group
    Then no error should occur
