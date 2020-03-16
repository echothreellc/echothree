Feature: Employee item
  An employee wants to add an item

  Background:
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an active item and discontinues it
    Given the employee Test is currently logged in
    And the employee Test begins entering a new item
    And the employee Test sets the item's type to REGULAR
    And the employee Test sets the item's use type to REGULAR
    And the employee Test sets the item's category to DEFAULT
    And the employee Test sets the item's accounting category to DEFAULT
    And the employee Test sets the item's purchasing category to DEFAULT
    And the employee Test sets the item's company to TEST_COMPANY
    And the employee Test sets the item's delivery type to PHYSICAL
    And the employee Test sets the item's inventory type to INVENTORY
    And the employee Test's item does not have serialized inventory
    And the employee Test's item is not exempt from shipping
    And the employee Test's item does allow club discounts
    And the employee Test's item does allow coupon discounts
    And the employee Test's item does allow associate payments
    And the employee Test sets the item's status to NEW_ACTIVE
    And the employee Test sets the item's unit of measure kind to BASIC
    And the employee Test sets the item's price type to FIXED
    And the employee Test adds the new item
    Then no error should occur
    And the employee Test sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds a cancel if not in stock item and discontinues it
    Given the employee Test is currently logged in
    And the employee Test begins entering a new item
    And the employee Test sets the item's type to REGULAR
    And the employee Test sets the item's use type to REGULAR
    And the employee Test sets the item's category to DEFAULT
    And the employee Test sets the item's accounting category to DEFAULT
    And the employee Test sets the item's purchasing category to DEFAULT
    And the employee Test sets the item's company to TEST_COMPANY
    And the employee Test sets the item's delivery type to PHYSICAL
    And the employee Test sets the item's inventory type to INVENTORY
    And the employee Test's item does not have serialized inventory
    And the employee Test's item is not exempt from shipping
    And the employee Test's item does allow club discounts
    And the employee Test's item does allow coupon discounts
    And the employee Test's item does allow associate payments
    And the employee Test sets the item's status to NEW_CANCEL_IF_NOT_IN_STOCK
    And the employee Test sets the item's unit of measure kind to BASIC
    And the employee Test sets the item's price type to FIXED
    And the employee Test adds the new item
    Then no error should occur
    And the employee Test sets the status of the last item added to CANCEL_IF_NOT_IN_STOCK_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds a discontinued item
    Given the employee Test is currently logged in
    And the employee Test begins entering a new item
    And the employee Test sets the item's type to REGULAR
    And the employee Test sets the item's use type to REGULAR
    And the employee Test sets the item's category to DEFAULT
    And the employee Test sets the item's accounting category to DEFAULT
    And the employee Test sets the item's purchasing category to DEFAULT
    And the employee Test sets the item's company to TEST_COMPANY
    And the employee Test sets the item's delivery type to PHYSICAL
    And the employee Test sets the item's inventory type to INVENTORY
    And the employee Test's item does not have serialized inventory
    And the employee Test's item is not exempt from shipping
    And the employee Test's item does allow club discounts
    And the employee Test's item does allow coupon discounts
    And the employee Test's item does allow associate payments
    And the employee Test sets the item's status to NEW_DISCONTINUED
    And the employee Test sets the item's unit of measure kind to BASIC
    And the employee Test sets the item's price type to FIXED
    And the employee Test adds the new item
    Then no error should occur
