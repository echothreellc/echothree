Feature: Employee location type
  An employee wants to add, edit, and delete a location name element

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a location name element, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new warehouse
    And the user sets the warehouse's warehouse name to "CucumberWarehouse"
    And the user sets the warehouse's warehouse type name to "DEFAULT"
    And the user sets the warehouse's name to "Cucumber Warehouse"
    And the user sets the warehouse to not be the default
    And the user sets the warehouse's sort order to "10"
    And the user sets the warehouse's inventory move printer group name to "DEFAULT"
    And the user sets the warehouse's picklist printer group mame to "DEFAULT"
    And the user sets the warehouse's packing list printer group name to "DEFAULT"
    And the user sets the warehouse's shipping manifest printer group name to "DEFAULT"
    And the user adds the new warehouse
    Then no error should occur
    When the user begins entering a new location type
    And the user sets the location type's warehouse name to the last warehouse added
    And the user sets the location type's location type name to "CucumberLocationType"
    And the user sets the location type to be the default
    And the user sets the location type's sort order to "10"
    And the user sets the location type's description to "Cucumber Location Type"
    And the user adds the new location type
    Then no error should occur
    When the user begins entering a new location name element
    And the user sets the location name element's warehouse name to the last warehouse added
    And the user sets the location name element's location type name to the last location type added
    And the user sets the location name element's location name element name to "CucumberLocationNameElement"
    And the user sets the location name element's offset to "0"
    And the user sets the location name element's length to "2"
    And the user sets the location name element's validation pattern to " ^[A-Z][A-Z]$"
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins specifying a location name element to edit
    And the user sets the location name element's warehouse name to the last warehouse added
    And the user sets the location name element's location type name to the last location type added
    And the user sets the location name element's location name element name to the last location name element added
    When the user begins editing the location name element
    Then no error should occur
    And the user sets the location name element's description to "Edited Cucumber Location Name Element"
    And the user finishes editing the location name element
    Then no error should occur
    When the user begins deleting a location name element
    And the user sets the location name element's warehouse name to the last warehouse added
    And the user sets the location name element's location type name to the last location type added
    And the user sets the location name element's location name element name to the last location name element added
    And the user deletes the location name element
    Then no error should occur
    When the user begins deleting a location type
    And the user sets the location type's warehouse name to the last warehouse added
    And the user sets the location type's location type name to the last location type added
    And the user deletes the location type
    Then no error should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur
