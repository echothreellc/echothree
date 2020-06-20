Feature: Employee item price
  An employee wants to add, edit, and delete an item price

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an item price, edits it, and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new item
    And the user sets the item's type to REGULAR
    And the user sets the item's use type to REGULAR
    And the user sets the item's category to DEFAULT
    And the user sets the item's accounting category to DEFAULT
    And the user sets the item's purchasing category to DEFAULT
    And the user sets the item's company to TEST_COMPANY
    And the user sets the item's delivery type to PHYSICAL
    And the user sets the item's inventory type to INVENTORY
    And the user's item does not have serialized inventory
    And the user's item is not exempt from shipping
    And the user's item does allow club discounts
    And the user's item does allow coupon discounts
    And the user's item does allow associate payments
    And the user sets the item's status to NEW_ACTIVE
    And the user sets the item's unit of measure kind to BASIC
    And the user sets the item's price type to FIXED
    And the user adds the new item
    Then no error should occur
    And the user begins entering a new item unit of measure type
    And the user sets the item unit of measure type's item to the last item added
    And the user sets the item unit of measure type's unit of measure type to EACH
    And the user sets the item unit of measure type's sort order to "1"
    And the user sets the item unit of measure type to be the default
    And the user adds the new item unit of measure type
    Then no error should occur
    And the user begins entering a new item price
    And the user sets the item price's item to the last item added
    And the user sets the item price's inventory condition to GOOD
    And the user sets the item price's unit of measure type to EACH
    And the user sets the item price's currency to USD
    And the user sets the item price's unit price to 5.99
    And the user adds the new item price
    Then no error should occur
    And the user begins specifying an item price to edit
    And the user sets the item price's item to the last item added
    And the user sets the item price's inventory condition to GOOD
    And the user sets the item price's unit of measure type to EACH
    And the user sets the item price's currency to USD
    And the user begins editing the item price
    Then no error should occur
    And the user sets the item price's unit price to 7.99
    And the user finishes editing the item price
    Then no error should occur
    And the user begins deleting an item price
    And the user sets the item price's item to the last item added
    And the user sets the item price's inventory condition to GOOD
    And the user sets the item price's unit of measure type to EACH
    And the user sets the item price's currency to USD
    And the user deletes the item price
    Then no error should occur
    And the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur
