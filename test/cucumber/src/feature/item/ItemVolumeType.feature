Feature: Employee item volume type
  An employee wants to add, edit, and delete a item volume type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a item volume type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new item volume type
    And the user sets the item volume type's name to CUCUMBER_ITEM_VOLUME_TYPE
    And the user sets the item volume type's sort order to "1"
    And the user sets the item volume type to be the default
    And the user sets the item volume type's description to "Cucumber Item Volume Type"
    And the user adds the new item volume type
    Then no error should occur
    When the user begins specifying a item volume type to edit
    And the user sets the item volume type's name to the last item volume type added
    When the user begins editing the item volume type
    Then no error should occur
    And the user sets the item volume type's sort order to "2"
    And the user sets the item volume type's description to "Edited Cucumber Item Volume Type"
    And the user finishes editing the item volume type
    Then no error should occur
    When the user begins deleting a item volume type
    And the user sets the item volume type's name to the last item volume type added
    And the user deletes the item volume type
    Then no error should occur

  Scenario: Existing employee adds a item volume type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new item volume type
    And the user sets the item volume type's name to EXAMPLE_ITEM_VOLUME_TYPE
    And the user sets the item volume type's sort order to "1"
    And the user sets the item volume type to be the default
    And the user adds the new item volume type
    Then the execution error DuplicateItemVolumeTypeName should occur
