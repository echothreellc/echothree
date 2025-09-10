Feature: Employee item weight type
  An employee wants to add, edit, and delete a item weight type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a item weight type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new item weight type
    And the user sets the item weight type's name to CUCUMBER_ITEM_WEIGHT_TYPE
    And the user sets the item weight type's sort order to "1"
    And the user sets the item weight type to be the default
    And the user sets the item weight type's description to "Cucumber Item Weight Type"
    And the user adds the new item weight type
    Then no error should occur
    When the user begins specifying a item weight type to edit
    And the user sets the item weight type's name to the last item weight type added
    When the user begins editing the item weight type
    Then no error should occur
    And the user sets the item weight type's sort order to "2"
    And the user sets the item weight type's description to "Edited Cucumber Item Weight Type"
    And the user finishes editing the item weight type
    Then no error should occur
    When the user begins deleting a item weight type
    And the user sets the item weight type's name to the last item weight type added
    And the user deletes the item weight type
    Then no error should occur

  Scenario: Existing employee adds a item weight type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new item weight type
    And the user sets the item weight type's name to EXAMPLE_ITEM_WEIGHT_TYPE
    And the user sets the item weight type's sort order to "1"
    And the user sets the item weight type to be the default
    And the user adds the new item weight type
    Then the execution error DuplicateItemWeightTypeName should occur
