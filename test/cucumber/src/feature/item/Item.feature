Feature: Employee item
  An employee wants to add an item

  Background:
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an item
    Given the employee Test is currently logged in
    And the employee Test adds a new item with type REGULAR and use type REGULAR and category DEFAULT and accounting category DEFAULT and purchasing category DEFAULT and the company TEST_COMPANY and delivery type PHYSICAL and inventory type INVENTORY and inventory serialized false and exempt from shipping false and allows club discounts true and allows coupon discounts true and allows associate payments true and has a status of NEW_ACTIVE and an unit of measure kind of BASIC and a price type of FIXED
    Then no error should occur
    And the employee Test sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur
