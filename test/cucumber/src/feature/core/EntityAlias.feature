Feature: Employee entity type
  An employee wants to add, edit, and delete an entity type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an entity alias
    Given the employee Test begins using the application
    When the user begins entering a new component vendor
    And the user sets the component vendor's name to TEST_COMPONENT_VENDOR
    And the user sets the component vendor's description to "Test Component Vendor"
    And the user adds the new component vendor
    Then no error should occur
    When the user begins entering a new entity type
    And the user sets the entity type's component vendor to the last component vendor added
    And the user sets the entity type's entity type to TEST_ENTITY_TYPE
    And the user sets the entity type to keep all history
    And the user sets the entity type's lock timeout to "12"
    And the user sets the entity type's lock timeout unit of measure type name to "HOUR"
    And the user sets the entity type to be extensible
    And the user sets the entity type's sort order to "1"
    And the user sets the entity type's description to "Test Entity Type"
    And the user adds the new entity type
    Then no error should occur
    When the user begins entering a new entity instance
    And the user sets the entity instance's component vendor to the last component vendor added
    And the user sets the entity instance's entity type to the last entity type added
    And the user adds the new entity instance
    Then no error should occur
    When the user begins entering a new entity alias type
    And the user sets the entity alias type's component vendor to "TEST_COMPONENT_VENDOR"
    And the user sets the entity alias type's entity type to "TEST_ENTITY_TYPE"
    And the user sets the entity alias type's name to "TEST_ENTITY_ALIAS_TYPE"
    And the user sets the entity alias type to be the default
    And the user sets the entity alias type's sort order to "1"
    And the user sets the entity alias type's description to "Test Entity Alias Type"
    And the user adds the new entity alias type
    When the user begins entering a new entity alias
    And the user sets the entity alias's entity to the last entity added
    And the user sets the entity alias's entity alias type to the last entity alias type added
    And the user sets the entity alias's alias to "SuperEntityAlias"
    And the user adds the new entity alias
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur

  Scenario: Existing employee adds and deletes an entity alias
    Given the employee Test begins using the application
    When the user begins entering a new component vendor
    And the user sets the component vendor's name to TEST_COMPONENT_VENDOR
    And the user sets the component vendor's description to "Test Component Vendor"
    And the user adds the new component vendor
    Then no error should occur
    When the user begins entering a new entity type
    And the user sets the entity type's component vendor to the last component vendor added
    And the user sets the entity type's entity type to TEST_ENTITY_TYPE
    And the user sets the entity type to keep all history
    And the user sets the entity type's lock timeout to "12"
    And the user sets the entity type's lock timeout unit of measure type name to "HOUR"
    And the user sets the entity type to be extensible
    And the user sets the entity type's sort order to "1"
    And the user sets the entity type's description to "Test Entity Type"
    And the user adds the new entity type
    Then no error should occur
    When the user begins entering a new entity instance
    And the user sets the entity instance's component vendor to the last component vendor added
    And the user sets the entity instance's entity type to the last entity type added
    And the user adds the new entity instance
    Then no error should occur
    When the user begins entering a new entity alias type
    And the user sets the entity alias type's component vendor to "TEST_COMPONENT_VENDOR"
    And the user sets the entity alias type's entity type to "TEST_ENTITY_TYPE"
    And the user sets the entity alias type's name to "TEST_ENTITY_ALIAS_TYPE"
    And the user sets the entity alias type to be the default
    And the user sets the entity alias type's sort order to "1"
    And the user sets the entity alias type's description to "Test Entity Alias Type"
    And the user adds the new entity alias type
    When the user begins entering a new entity alias
    And the user sets the entity alias's entity to the last entity added
    And the user sets the entity alias's entity alias type to the last entity alias type added
    And the user sets the entity alias's alias to "SuperEntityAlias"
    And the user adds the new entity alias
    Then no error should occur
    When the user begins deleting an entity alias
    And the user sets the entity alias's entity to the last entity added
    And the user sets the entity alias's entity alias type to the last entity alias type added
    And the user deletes the entity alias
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur

  Scenario: Existing employee adds and edits an entity alias
    Given the employee Test begins using the application
    When the user begins entering a new component vendor
    And the user sets the component vendor's name to TEST_COMPONENT_VENDOR
    And the user sets the component vendor's description to "Test Component Vendor"
    And the user adds the new component vendor
    Then no error should occur
    When the user begins entering a new entity type
    And the user sets the entity type's component vendor to the last component vendor added
    And the user sets the entity type's entity type to TEST_ENTITY_TYPE
    And the user sets the entity type to keep all history
    And the user sets the entity type's lock timeout to "12"
    And the user sets the entity type's lock timeout unit of measure type name to "HOUR"
    And the user sets the entity type to be extensible
    And the user sets the entity type's sort order to "1"
    And the user sets the entity type's description to "Test Entity Type"
    And the user adds the new entity type
    Then no error should occur
    When the user begins entering a new entity instance
    And the user sets the entity instance's component vendor to the last component vendor added
    And the user sets the entity instance's entity type to the last entity type added
    And the user adds the new entity instance
    Then no error should occur
    When the user begins entering a new entity alias type
    And the user sets the entity alias type's component vendor to "TEST_COMPONENT_VENDOR"
    And the user sets the entity alias type's entity type to "TEST_ENTITY_TYPE"
    And the user sets the entity alias type's name to "TEST_ENTITY_ALIAS_TYPE"
    And the user sets the entity alias type to be the default
    And the user sets the entity alias type's sort order to "1"
    And the user sets the entity alias type's description to "Test Entity Alias Type"
    And the user adds the new entity alias type
    When the user begins entering a new entity alias
    And the user sets the entity alias's entity to the last entity added
    And the user sets the entity alias's entity alias type to the last entity alias type added
    And the user sets the entity alias's alias to "SuperEntityAlias"
    And the user adds the new entity alias
    Then no error should occur
    When the user begins specifying an entity alias to edit
    And the user sets the entity alias's entity to the last entity added
    And the user sets the entity alias's entity alias type to the last entity alias type added
    When the user begins editing the entity alias
    Then no error should occur
    And the user sets the entity alias's alias to "EditedEntityAlias"
    And the user finishes editing the entity alias
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur
