Feature: Employee filter
  An employee wants to add, edit, and delete a filter

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a filter, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new filter
    And the user sets the filter's filter kind name to PRICE
    And the user sets the filter's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter's name to TEST_FILTER_TYPE
    And the user sets the filter's initial filter adjustment name to "EXAMPLE_ITEM_PRICE"
    And the user sets the filter's filter item selector name to "ACTIVE_ITEM"
    And the user sets the filter's sort order to "1"
    And the user sets the filter to be the default
    And the user sets the filter's description to "Test Filter"
    And the user adds the new filter
    Then no error should occur
    When the user begins specifying a filter to edit
    And the user sets the filter's filter kind name to PRICE
    And the user sets the filter's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter's name to the last filter added
    When the user begins editing the filter
    Then no error should occur
    And the user sets the filter's sort order to "2"
    And the user sets the filter's description to "Test Edited Filter"
    And the user finishes editing the filter
    Then no error should occur
    When the user begins deleting a filter
    And the user sets the filter's filter kind name to PRICE
    And the user sets the filter's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter's name to the last filter added
    And the user deletes the filter
    Then no error should occur

  Scenario: Existing employee adds a filter with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new filter
    And the user sets the filter's filter kind name to PRICE
    And the user sets the filter's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter's name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter's initial filter adjustment name to "EXAMPLE_ITEM_PRICE"
    And the user sets the filter's filter item selector name to "ACTIVE_ITEM"
    And the user sets the filter's sort order to "1"
    And the user sets the filter to be the default
    And the user adds the new filter
    Then the execution error DuplicateFilterName should occur
