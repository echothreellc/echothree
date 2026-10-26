Feature: Employee entity attribute group
  An employee wants to add, edit, and delete an entity attribute group

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an entity attribute group, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's name to "TEST_ENTITY_ATTRIBUTE_GROUP"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user sets the entity attribute group's description to "Test Entity Attribute Group"
    And the user adds the new entity attribute group
    Then no error should occur
    When the user begins specifying an entity attribute group to edit
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's name to the last entity attribute group added
    When the user begins editing the entity attribute group
    Then no error should occur
    And the user sets the entity attribute group's sort order to "2"
    And the user sets the entity attribute group's description to "Test Edited Entity Attribute Group"
    And the user finishes editing the entity attribute group
    Then no error should occur
    When the user begins deleting an entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user deletes the entity attribute group
    Then no error should occur

  Scenario: Existing employee adds an entity attribute group without a name, edits it, and then deletes it using names
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user sets the entity attribute group's description to "Test Entity Attribute Group"
    And the user adds the new entity attribute group
    Then no error should occur
    When the user begins specifying an entity attribute group to edit
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's name to the last entity attribute group added
    When the user begins editing the entity attribute group
    Then no error should occur
    And the user sets the entity attribute group's sort order to "2"
    And the user sets the entity attribute group's description to "Test Edited Entity Attribute Group"
    And the user finishes editing the entity attribute group
    Then no error should occur
    When the user begins deleting an entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's name to the last entity attribute group added
    And the user deletes the entity attribute group
    Then no error should occur

  Scenario: Existing employee adds an entity attribute group without a name, edits it, and then deletes it using entity reference
    Given the employee Test begins using the application
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "Item"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user sets the entity attribute group's description to "Test Entity Attribute Group"
    And the user adds the new entity attribute group
    Then no error should occur
    When the user begins specifying an entity attribute group to edit
    And the user identifies the last entity attribute group by entity reference
    When the user begins editing the entity attribute group
    Then no error should occur
    And the user sets the entity attribute group's sort order to "2"
    And the user sets the entity attribute group's description to "Test Edited Entity Attribute Group"
    And the user finishes editing the entity attribute group
    Then no error should occur
    When the user begins deleting an entity attribute group
    And the user identifies the last entity attribute group by entity reference
    And the user deletes the entity attribute group
    Then no error should occur

  Scenario: Group names and defaults are independent for each owning type, including empty groups
    When the user begins entering a new entity type
    And the user sets the entity type's component vendor to "ECHO_THREE"
    And the user sets the entity type's entity type to "TestGroupOwnerA"
    And the user sets the entity type to "keep" all history
    And the user sets the entity type to "be" extensible
    And the user sets the entity type's sort order to "0"
    And the user adds the new entity type
    Then no error should occur
    When the user begins entering a new entity type
    And the user sets the entity type's component vendor to "ECHO_THREE"
    And the user sets the entity type's entity type to "TestGroupOwnerB"
    And the user sets the entity type to "keep" all history
    And the user sets the entity type to "be" extensible
    And the user sets the entity type's sort order to "0"
    And the user adds the new entity type
    Then no error should occur
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "TestGroupOwnerA"
    And the user sets the entity attribute group's name to "SHARED_NAME"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user adds the new entity attribute group
    Then no error should occur
    Then entity attribute group "SHARED_NAME" is listed for "ECHO_THREE" "TestGroupOwnerA" and is "default"
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "TestGroupOwnerB"
    And the user sets the entity attribute group's name to "SHARED_NAME"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user adds the new entity attribute group
    Then no error should occur
    Then entity attribute group "SHARED_NAME" is listed for "ECHO_THREE" "TestGroupOwnerB" and is "default"
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "TestGroupOwnerA"
    And the user sets the entity attribute group's name to "SHARED_NAME"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user adds the new entity attribute group
    Then the execution error DuplicateEntityAttributeGroupName should occur
    When the user begins entering a new entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "TestGroupOwnerA"
    And the user sets the entity attribute group's name to "SECOND"
    And the user sets the entity attribute group's sort order to "1"
    And the user sets the entity attribute group to "be" the default
    And the user adds the new entity attribute group
    Then no error should occur
    Then entity attribute group "SHARED_NAME" is listed for "ECHO_THREE" "TestGroupOwnerA" and is "not default"
    Then entity attribute group "SECOND" is listed for "ECHO_THREE" "TestGroupOwnerA" and is "default"
    Then entity attribute group "SHARED_NAME" is listed for "ECHO_THREE" "TestGroupOwnerB" and is "default"
    When the user begins deleting an entity attribute group
    And the user sets the entity attribute group's component vendor to "ECHO_THREE"
    And the user sets the entity attribute group's entity type to "TestGroupOwnerA"
    And the user sets the entity attribute group's name to "SECOND"
    And the user deletes the entity attribute group
    Then no error should occur
    And entity attribute group "SHARED_NAME" is listed for "ECHO_THREE" "TestGroupOwnerA" and is "default"
    And entity attribute group "SHARED_NAME" is listed for "ECHO_THREE" "TestGroupOwnerB" and is "default"
    When the user begins deleting an entity type
    And the user sets the entity type's component vendor to "ECHO_THREE"
    And the user sets the entity type's entity type to "TestGroupOwnerA"
    And the user deletes the entity type
    Then no error should occur
    When the user begins deleting an entity type
    And the user sets the entity type's component vendor to "ECHO_THREE"
    And the user sets the entity type's entity type to "TestGroupOwnerB"
    And the user deletes the entity type
    Then no error should occur
