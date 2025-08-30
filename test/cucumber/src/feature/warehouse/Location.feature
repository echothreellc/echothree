Feature: Employee location
  An employee wants to add, edit, and delete a location

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a location, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new warehouse
    And the user sets the warehouse's warehouse name to "CucumberWarehouse"
    And the user sets the warehouse's name to "Cucumber Warehouse"
    And the user sets the warehouse's warehouse type name to "DEFAULT"
    And the user sets the warehouse to not be the default
    And the user sets the warehouse's sort order to "10"
    And the user sets the warehouse's inventory move printer group name to "DEFAULT"
    And the user sets the warehouse's picklist printer group mame to "DEFAULT"
    And the user sets the warehouse's packing list printer group name to "DEFAULT"
    And the user sets the warehouse's shipping manifest printer group name to "DEFAULT"
    And the user adds the new warehouse
    Then no error should occur
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "AA"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then no error should occur
    When the user begins specifying a location to edit
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    When the user begins editing the location
    Then no error should occur
    And the user sets the location's description to "Edited Cucumber Location"
    And the user finishes editing the location
    Then no error should occur
    When the user begins setting the status of a location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    And the user sets the location's status to "ACTIVE_TO_INVENTORY_PREP"
    And the user sets the status of the location
    Then no error should occur
    When the user begins deleting a location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    And the user deletes the location
    Then no error should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location type with no validation pattern and then adds a location
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "AA"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then no error should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee attempts to add a location with a name that does not match the validation pattern
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "00"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then the execution error InvalidLocationName should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location type with no validation pattern and then attempts to add a location with a name that is too long
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "0000"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then the execution error InvalidLocationName should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location type with no validation pattern and then attempts to add a location with a name that is too short
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "0"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then the execution error InvalidLocationName should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location type with no validation pattern and then adds and edits a location
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "AA"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then no error should occur
    When the user begins specifying a location to edit
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    When the user begins editing the location
    Then no error should occur
    And the user sets the location's new location name to "BB"
    And the user finishes editing the location
    Then no error should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location and then edits it trying to use a name that does not match the validation pattern
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "AA"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then no error should occur
    When the user begins specifying a location to edit
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    When the user begins editing the location
    Then no error should occur
    And the user sets the location's new location name to "00"
    And the user finishes editing the location
    Then the execution error InvalidLocationName should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location type with no validation pattern and then attempts edits it trying to use a name that is too long
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "00"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the location's description to "Cucumber Location"
    And the user adds the new location
    Then no error should occur
    When the user begins specifying a location to edit
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    When the user begins editing the location
    Then no error should occur
    And the user sets the location's new location name to "0000"
    And the user finishes editing the location
    Then the execution error InvalidLocationName should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur

  Scenario: Existing employee adds a location type with no validation pattern and then attempts edits it trying to use a name that is too short
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
    When the user begins entering a new inventory location group
    And the user sets the inventory location group's warehouse name to the last warehouse added
    And the user sets the inventory location group's inventory location group name to "CucumberInventoryLocationGroup"
    And the user sets the inventory location group to be the default
    And the user sets the inventory location group's sort order to "10"
    And the user sets the inventory location group's description to "Cucumber Inventory Location Group"
    And the user adds the new inventory location group
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
    And the user sets the location name element's description to "Cucumber Location Name Element"
    And the user adds the new location name element
    Then no error should occur
    When the user begins entering a new location
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to "00"
    And the user sets the location's location type name to "CucumberLocationType"
    And the user sets the location's location use type name to "PICKING"
    And the user sets the location's velocity to "10"
    And the user sets the location's inventory location group name to "CucumberInventoryLocationGroup"
    And the user adds the new location
    Then no error should occur
    When the user begins specifying a location to edit
    And the user sets the location's warehouse name to the last warehouse added
    And the user sets the location's location name to the last location added
    When the user begins editing the location
    Then no error should occur
    And the user sets the location's new location name to "0"
    And the user finishes editing the location
    Then the execution error InvalidLocationName should occur
    When the user begins deleting a warehouse
    And the user sets the warehouse's warehouse name to the last warehouse added
    And the user deletes the warehouse
    Then no error should occur
