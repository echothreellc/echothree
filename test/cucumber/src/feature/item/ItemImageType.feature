Feature: Employee item image type
  An employee wants to add, edit, and delete a item image type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a item image type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new item image type
    And the user sets the item image type's name to TEST_ITEM_IMAGE_TYPE
    And the user sets the item image type's sort order to "1"
    And the user sets the item image type to be the default
    And the user sets the item image type's description to "Test Item Image Type"
    And the user adds the new item image type
    Then no error should occur
    When the user begins specifying a item image type to edit
    And the user sets the item image type's name to the last item image type added
    When the user begins editing the item image type
    Then no error should occur
    And the user sets the item image type's sort order to "2"
    And the user sets the item image type's description to "Test Edited Item Image Type"
    And the user finishes editing the item image type
    Then no error should occur
    When the user begins deleting a item image type
    And the user sets the item image type's name to the last item image type added
    And the user deletes the item image type
    Then no error should occur

  Scenario: Existing employee adds a item image type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new item image type
    And the user sets the item image type's name to PHOTO
    And the user sets the item image type's sort order to "1"
    And the user sets the item image type to be the default
    And the user adds the new item image type
    Then the execution error DuplicateItemImageTypeName should occur
