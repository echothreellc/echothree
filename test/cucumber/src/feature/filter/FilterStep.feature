Feature: Employee filter step
  An employee wants to add, edit, and delete a filter step

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a filter step, edits it, and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new filter
    And the user sets the filter's filter kind name to PRICE
    And the user sets the filter's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter's name to TEST_FILTER
    And the user sets the filter's initial filter adjustment name to "EXAMPLE_ITEM_PRICE"
    And the user sets the filter's filter item selector name to "ACTIVE_ITEM"
    And the user sets the filter's sort order to "1"
    And the user sets the filter to be the default
    And the user sets the filter's description to "Test Filter"
    And the user adds the new filter
    Then no error should occur
    And the user begins entering a new filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to TEST_FILTER
    And the user sets the filter step's name to TEST_FILTER_STEP
    And the user sets the filter step's description to "Test Filter Step"
    And the user adds the new filter step
    Then no error should occur
    And the user begins specifying a filter step to edit
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to TEST_FILTER
    And the user sets the filter step's name to the last filter step added
    And the user begins editing the filter step
    Then no error should occur
    And the user sets the filter step's description to "Test Edited Filter Step"
    And the user finishes editing the filter step
    Then no error should occur
    And the user begins deleting a filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to TEST_FILTER
    And the user sets the filter step's name to the last filter step added
    And the user deletes the filter step
    Then no error should occur
    And the user begins deleting a filter
    And the user sets the filter's filter kind name to PRICE
    And the user sets the filter's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter's name to the last filter added
    And the user deletes the filter
    Then no error should occur
