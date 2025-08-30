Feature: Employee item
  An employee wants to add an item

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an active item and discontinues it
    Given the employee Test begins using the application
    When the user begins entering a new item
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
    And the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds a cancel if not in stock item and discontinues it
    Given the employee Test begins using the application
    When the user begins entering a new item
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
    And the user sets the item's status to NEW_CANCEL_IF_NOT_IN_STOCK
    And the user sets the item's unit of measure kind to BASIC
    And the user sets the item's price type to FIXED
    And the user adds the new item
    Then no error should occur
    And the user sets the status of the last item added to CANCEL_IF_NOT_IN_STOCK_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds a discontinued item
    Given the employee Test begins using the application
    When the user begins entering a new item
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
    And the user sets the item's status to NEW_DISCONTINUED
    And the user sets the item's unit of measure kind to BASIC
    And the user sets the item's price type to FIXED
    And the user adds the new item
    Then no error should occur
