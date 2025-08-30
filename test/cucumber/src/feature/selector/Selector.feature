Feature: Employee selector
  An employee wants to add, edit, and delete a selector

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a selector, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new selector
    And the user sets the selector's selector kind name to ITEM
    And the user sets the selector's selector type name to FILTER
    And the user sets the selector's name to TEST_SELECTOR_TYPE
    And the user sets the selector's sort order to "1"
    And the user sets the selector to be the default
    And the user sets the selector's description to "Test Selector"
    And the user adds the new selector
    Then no error should occur
    When the user begins specifying a selector to edit
    And the user sets the selector's selector kind name to ITEM
    And the user sets the selector's selector type name to FILTER
    And the user sets the selector's name to the last selector added
    When the user begins editing the selector
    Then no error should occur
    And the user sets the selector's sort order to "2"
    And the user sets the selector's description to "Test Edited Selector"
    And the user finishes editing the selector
    Then no error should occur
    When the user begins deleting a selector
    And the user sets the selector's selector kind name to ITEM
    And the user sets the selector's selector type name to FILTER
    And the user sets the selector's name to the last selector added
    And the user deletes the selector
    Then no error should occur

  Scenario: Existing employee adds a selector with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new selector
    And the user sets the selector's selector kind name to ITEM
    And the user sets the selector's selector type name to OFFER
    And the user sets the selector's name to EXAMPLE_OFFER_ITEM_1
    And the user sets the selector's sort order to "1"
    And the user sets the selector to be the default
    And the user adds the new selector
    Then the execution error DuplicateSelectorName should occur
