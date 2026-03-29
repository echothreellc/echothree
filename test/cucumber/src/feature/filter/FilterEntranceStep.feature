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

  Scenario: Existing employee adds a filter entrance step and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step's name to CUCUMBER_FILTER_STEP_1
    And the user sets the filter step's description to "Cucumber Filter Step 1"
    And the user adds the new filter step
    Then no error should occur
    When the user begins entering a new filter entrance step
    And the user sets the filter entrance step's filter kind name to PRICE
    And the user sets the filter entrance step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter entrance step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter entrance step's filter step name to CUCUMBER_FILTER_STEP_1
    And the user adds the new filter entrance step
    Then no error should occur
    When the user begins deleting a filter entrance step
    And the user sets the filter entrance step's filter kind name to PRICE
    And the user sets the filter entrance step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter entrance step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter entrance step's filter step name to CUCUMBER_FILTER_STEP_1
    And the user deletes the filter entrance step
    Then no error should occur
    When the user begins deleting a filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step's name to the last filter step added
    And the user deletes the filter step
    Then no error should occur

  Scenario: Existing employee adds a filter entrance step and attempts deleting the filter step while in-use
    Given the employee Test begins using the application
    When the user begins entering a new filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step's name to CUCUMBER_FILTER_STEP_1
    And the user sets the filter step's description to "Cucumber Filter Step 1"
    And the user adds the new filter step
    Then no error should occur
    When the user begins entering a new filter entrance step
    And the user sets the filter entrance step's filter kind name to PRICE
    And the user sets the filter entrance step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter entrance step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter entrance step's filter step name to CUCUMBER_FILTER_STEP_1
    And the user adds the new filter entrance step
    Then no error should occur
    When the user begins deleting a filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step's name to the last filter step added
    And the user deletes the filter step
    Then the execution error CannotDeleteFilterStepInUse should occur
    When the user begins deleting a filter entrance step
    And the user sets the filter entrance step's filter kind name to PRICE
    And the user sets the filter entrance step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter entrance step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter entrance step's filter step name to CUCUMBER_FILTER_STEP_1
    And the user deletes the filter entrance step
    Then no error should occur
    When the user begins deleting a filter step
    And the user sets the filter step's filter kind name to PRICE
    And the user sets the filter step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step's name to the last filter step added
    And the user deletes the filter step
    Then no error should occur

  Scenario: Existing employee adds a duplicate filter entrance step and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new filter entrance step
    And the user sets the filter entrance step's filter kind name to PRICE
    And the user sets the filter entrance step's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter entrance step's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter entrance step's filter step name to EXAMPLE_FILTER_STEP_1
    And the user adds the new filter entrance step
    Then the execution error DuplicateFilterEntranceStep should occur
