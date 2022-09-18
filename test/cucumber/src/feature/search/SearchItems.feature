Feature: Search items
  A customer searches items

  Scenario: An anonymous customer searches items by item name
    Given the customer Test begins using the application
    And the user begins entering a new item search
    And the user sets the item search's search type to "ITEM_MAINTENANCE"
    And the user sets the item search's item name or alias to "minimal"
    When the user executes the new item search
    Then no error should occur
    And the user's search results contain "1" result
