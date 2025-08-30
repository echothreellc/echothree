Feature: Search items
  A customer searches items

  Scenario: An anonymous customer searches items by item name
    Given the customer Test begins using the application
    When the user begins entering a new item search
    And the user sets the item search's search type to "ITEM_MAINTENANCE"
    And the user sets the item search's item name or alias to "minimal"
    When the user executes the new item search
    Then no error should occur
    And the user's search results contain "1" result

  Scenario: An anonymous customer searches items by item name and clears the results
    Given the customer Test begins using the application
    When the user begins entering a new item search
    And the user sets the item search's search type to "ITEM_MAINTENANCE"
    And the user sets the item search's item name or alias to "minimal"
    When the user executes the new item search
    Then no error should occur
    And the user's search results contain "1" result
    When the user begins clearing the item results
    And the user sets the clear item results' search type to "ITEM_MAINTENANCE"
    When the user clears the item results
    Then no error should occur

  Scenario: An anonymous customer searches items by item name and acts on one of the results
    Given the customer Test begins using the application
    When the user begins entering a new item search
    And the user sets the item search's search type to "ITEM_MAINTENANCE"
    And the user sets the item search's item name or alias to "minimal"
    When the user executes the new item search
    Then no error should occur
    And the user's search results contain "1" result
    When the user begins recording an action on a result of the item search
    And the user sets the item search result action's search type to "ITEM_MAINTENANCE"
    And the user sets the item search result action's action to "SELECTED"
    And the user sets the item search result action's item to "MINIMAL"
    When the user records an action on a result of the item search
    Then no error should occur
