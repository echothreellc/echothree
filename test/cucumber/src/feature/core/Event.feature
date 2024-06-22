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

  Scenario: Existing employee sends a read event
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
    When the user begins entering a new event
    And the user sets the event's entity instance to the last entity instance added
    And the user sets the event's event type to READ
    And the user sends the new event
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur

  Scenario: Existing employee sends a modify event
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
    When the user begins entering a new event
    And the user sets the event's entity instance to the last entity instance added
    And the user sets the event's event type to MODIFY
    And the user sends the new event
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur

  Scenario: Existing employee sends a touch event
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
    When the user begins entering a new event
    And the user sets the event's entity instance to the last entity instance added
    And the user sets the event's event type to TOUCH
    And the user sends the new event
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur

  Scenario: Existing employee attempts to send a create event
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
    When the user begins entering a new event
    And the user sets the event's entity instance to the last entity instance added
    And the user sets the event's event type to CREATE
    And the user sends the new event
    Then the execution error InvalidEventType should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur

  Scenario: Existing employee attempts to send a delete event
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
    When the user begins entering a new event
    And the user sets the event's entity instance to the last entity instance added
    And the user sets the event's event type to DELETE
    And the user sends the new event
    Then the execution error InvalidEventType should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur
