Feature: Employee entity attribute
  An employee wants to add and delete an entity attribute

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an integer entity attribute, edits it, and then deletes it
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
    When the user begins specifying an entity attribute to edit
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    When the user begins editing the entity attribute
    Then no error should occur
    And the user sets the entity attribute to not track revisions when modified
    And the user sets the entity attribute's upper integer range to "1000"
    And the user sets the entity attribute's upper integer limit to "950"
    And the user sets the entity attribute's lower integer limit to "550"
    And the user sets the entity attribute's lower integer range to "500"
    And the user sets the entity attribute's description to "Test Edited Integer Attribute"
    And the user finishes editing the entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a long entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to LONG
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's upper long range to "100"
    And the user sets the entity attribute's upper long limit to "90"
    And the user sets the entity attribute's lower long limit to "10"
    And the user sets the entity attribute's lower long range to "0"
    And the user sets the entity attribute's unit of measure kind to BASIC
    And the user sets the entity attribute's unit of measure type to EACH
    And the user sets the entity attribute's description to "Test Long Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins specifying an entity attribute to edit
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    When the user begins editing the entity attribute
    Then no error should occur
    And the user sets the entity attribute to not track revisions when modified
    And the user sets the entity attribute's upper long range to "1000"
    And the user sets the entity attribute's upper long limit to "950"
    And the user sets the entity attribute's lower long limit to "550"
    And the user sets the entity attribute's lower long range to "500"
    And the user sets the entity attribute's unit of measure type to DOZEN
    And the user sets the entity attribute's description to "Test Edited Long Attribute"
    And the user finishes editing the entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a BLOB entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to BLOB
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute to check the content web address when requested
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test BLOB Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a CLOB entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to CLOB
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test CLOB Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a string entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to STRING
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's validation pattern to "^([0-9a-zA-Z]*)$"
    And the user sets the entity attribute's description to "Test String Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a boolean entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to BOOLEAN
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Boolean Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a collection entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to COLLECTION
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Entity Collection Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a date entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to DATE
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Date Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a time entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to TIME
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Time Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds an entity entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to ENTITY
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Entity Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a geopoint entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to GEOPOINT
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Geo Point Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a name entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to NAME
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Name Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a list item entity attribute and then deletes it
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
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds a multiple list item entity attribute and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to MULTIPLELISTITEM
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Multiple List Item Attribute"
    And the user adds the new entity attribute
    Then no error should occur
    When the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur

  Scenario: Existing employee adds an entity attribute to an entity type that is not extensible and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHO_THREE
    And the user sets the entity attribute's entity type to ItemAlias
    And the user sets the entity attribute's entity attribute type to INTEGER
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user sets the entity attribute's description to "Test Integer Attribute"
    And the user adds the new entity attribute
    Then the execution error EntityTypeIsNotExtensible should occur
