Feature: Employee entity attribute
  An employee wants to add, edit, and delete an entity attribute entity attribute group group

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an entity attribute entity attribute group and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to INTEGER
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's upper integer range to "100"
    And the user sets the entity attribute's upper integer limit to "90"
    And the user sets the entity attribute's lower integer limit to "10"
    And the user sets the entity attribute's lower integer range to "0"
    And the user sets the entity attribute's description to "Test Integer Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to be the default
    And the user sets the entity attribute group's description to "Test Entity Attribute Group"
    And the user adds the new entity attribute group
    Then no error should occur
    When the user begins entering a new entity attribute entity attribute group
    And the user sets the entity attribute entity attribute group's component vendor to ECHO_THREE
    And the user sets the entity attribute entity attribute group's entity type to Item
    And the user sets the entity attribute entity attribute group's entity attribute to the last entity attribute added
    And the user sets the entity attribute entity attribute group's entity attribute group to the last entity attribute group added
    And the user sets the entity attribute entity attribute group's sort order to "1"
    And the user adds the new entity attribute entity attribute group
    Then no error should occur
    When the user begins deleting an entity attribute entity attribute group
    And the user sets the entity attribute entity attribute group's component vendor to ECHO_THREE
    And the user sets the entity attribute entity attribute group's entity type to Item
    And the user sets the entity attribute entity attribute group's entity attribute to the last entity attribute added
    And the user sets the entity attribute entity attribute group's entity attribute group to the last entity attribute group added
    And the user deletes the entity attribute entity attribute group
    Then no error should occur
    When the user begins deleting an entity attribute group
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user deletes the entity attribute group
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur
