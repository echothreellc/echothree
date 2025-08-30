Feature: Employee item description type use type
  An employee wants to add, edit, and delete a item description type use type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a item description type use type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new item description type use type
    And the user sets the item description type use type's name to TEST_ITEM_DESCRIPTION_TYPE_USE_TYPE
    And the user sets the item description type use type's sort order to "1"
    And the user sets the item description type use type to be the default
    And the user sets the item description type use type's description to "Test Item Description Type Use Type"
    And the user adds the new item description type use type
    Then no error should occur
    When the user begins specifying a item description type use type to edit
    And the user sets the item description type use type's name to the last item description type use type added
    When the user begins editing the item description type use type
    Then no error should occur
    And the user sets the item description type use type's sort order to "2"
    And the user sets the item description type use type's description to "Test Edited Item Description Type Use Type"
    And the user finishes editing the item description type use type
    Then no error should occur
    When the user begins deleting a item description type use type
    And the user sets the item description type use type's name to the last item description type use type added
    And the user deletes the item description type use type
    Then no error should occur

  Scenario: Existing employee adds a item description type use type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new item description type use type
    And the user sets the item description type use type's name to SCREENSHOTS
    And the user sets the item description type use type's sort order to "1"
    And the user sets the item description type use type to be the default
    And the user adds the new item description type use type
    Then the execution error DuplicateItemDescriptionTypeUseTypeName should occur
