Feature: Employee filter type
  An employee wants to add, edit, and delete a filter type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a filter type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new filter type
    And the user sets the filter type's filter kind name to PRICE
    And the user sets the filter type's name to TEST_FILTER_TYPE
    And the user sets the filter type's sort order to "1"
    And the user sets the filter type to be the default
    And the user sets the filter type's description to "Test Filter Type"
    And the user adds the new filter type
    Then no error should occur
    When the user begins specifying a filter type to edit
    And the user sets the filter type's filter kind name to PRICE
    And the user sets the filter type's name to the last filter type added
    When the user begins editing the filter type
    Then no error should occur
    And the user sets the filter type's sort order to "2"
    And the user sets the filter type's description to "Test Edited Filter Type"
    And the user finishes editing the filter type
    Then no error should occur
    When the user begins deleting a filter type
    And the user sets the filter type's filter kind name to PRICE
    And the user sets the filter type's name to the last filter type added
    And the user deletes the filter type
    Then no error should occur

  Scenario: Existing employee adds a filter type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new filter type
    And the user sets the filter type's filter kind name to PRICE
    And the user sets the filter type's name to OFFER_ITEM_PRICE
    And the user sets the filter type's sort order to "1"
    And the user sets the filter type to be the default
    And the user adds the new filter type
    Then the execution error DuplicateFilterTypeName should occur
