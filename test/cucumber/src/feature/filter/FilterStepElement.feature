Feature: Employee filter step
  An employee wants to add, edit, and delete a filter step

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur


  Scenario: Existing employee adds a filter step element, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new filter step element
    And the user sets the filter step element's filter kind name to PRICE
    And the user sets the filter step element's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step element's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step element's filter step name to EXAMPLE_FILTER_STEP_1
    And the user sets the filter step element's name to CUCUMBER_FILTER_STEP_ELEMENT_1
    And the user sets the filter step element's filter adjustment name to "EXAMPLE_ITEM_PRICE"
    And the user sets the filter step element's description to "Cucumber Filter Step Element 1"
    And the user adds the new filter step element
    Then no error should occur
    When the user begins specifying a filter step element to edit
    And the user sets the filter step element's filter kind name to PRICE
    And the user sets the filter step element's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step element's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step element's filter step name to EXAMPLE_FILTER_STEP_1
    And the user sets the filter step element's name to the last filter step element added
    When the user begins editing the filter step element
    Then no error should occur
    And the user sets the filter step element's description to "Edited Cucumber Filter Step Element 1"
    And the user finishes editing the filter step element
    Then no error should occur
    When the user begins deleting a filter step element
    And the user sets the filter step element's filter kind name to PRICE
    And the user sets the filter step element's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step element's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step element's filter step name to EXAMPLE_FILTER_STEP_1
    And the user sets the filter step element's name to the last filter step element added
    And the user deletes the filter step element
    Then no error should occur

  Scenario: Existing employee adds a filter step element with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new filter step element
    And the user sets the filter step element's filter kind name to PRICE
    And the user sets the filter step element's filter type name to OFFER_ITEM_PRICE
    And the user sets the filter step element's filter name to EXAMPLE_OFFER_ITEM_PRICE_FILTER
    And the user sets the filter step element's filter step name to EXAMPLE_FILTER_STEP_1
    And the user sets the filter step element's name to EXAMPLE_FILTER_STEP_1_ELEMENT_1
    And the user sets the filter step element's filter adjustment name to "EXAMPLE_ITEM_PRICE"
    And the user sets the filter step element's description to "Cucumber Offer Item Price Filter Step 1 Element 1"
    And the user adds the new filter step element
    Then the execution error DuplicateFilterStepElementName should occur
