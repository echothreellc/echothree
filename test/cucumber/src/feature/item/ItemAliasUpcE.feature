Feature: Employee UPC-E item alias
  An employee wants to add, edit, and delete an item alias that is a UPC-E

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an item, then a valid UPC-E item alias, edits it, and deletes it
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
    When the user begins entering a new item unit of measure type
    And the user sets the item unit of measure type's item to the last item added
    And the user sets the item unit of measure type's unit of measure type to EACH
    And the user sets the item unit of measure type's sort order to "1"
    And the user sets the item unit of measure type to be the default
    And the user adds the new item unit of measure type
    Then no error should occur
    When the user begins entering a new item alias
    And the user sets the item alias's item to the last item added
    And the user sets the item alias's unit of measure type to "EACH"
    And the user sets the item alias's item alias type to "UPC_E"
    And the user sets the item alias's alias to "04210007"
    And the user adds the new item alias
    Then no error should occur
    When the user begins deleting an item alias
    And the user sets the item alias's alias to "04210007"
    And the user deletes the item alias
    Then no error should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a UPC-E item alias with an incorrect checksum
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
    When the user begins entering a new item unit of measure type
    And the user sets the item unit of measure type's item to the last item added
    And the user sets the item unit of measure type's unit of measure type to EACH
    And the user sets the item unit of measure type's sort order to "1"
    And the user sets the item unit of measure type to be the default
    And the user adds the new item unit of measure type
    Then no error should occur
    When the user begins entering a new item alias
    And the user sets the item alias's item to the last item added
    And the user sets the item alias's unit of measure type to "EACH"
    And the user sets the item alias's item alias type to "UPC_E"
    And the user sets the item alias's alias to "04210006"
    And the user adds the new item alias
    Then the execution error IncorrectUpcAChecksum should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a UPC-E item alias with an incorrect character
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
    When the user begins entering a new item unit of measure type
    And the user sets the item unit of measure type's item to the last item added
    And the user sets the item unit of measure type's unit of measure type to EACH
    And the user sets the item unit of measure type's sort order to "1"
    And the user sets the item unit of measure type to be the default
    And the user adds the new item unit of measure type
    Then no error should occur
    When the user begins entering a new item alias
    And the user sets the item alias's item to the last item added
    And the user sets the item alias's unit of measure type to "EACH"
    And the user sets the item alias's item alias type to "UPC_E"
    And the user sets the item alias's alias to "04210A05"
    And the user adds the new item alias
    Then the execution error IncorrectUpcECharacter should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a UPC-E item alias with an incorrect length
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
    When the user begins entering a new item unit of measure type
    And the user sets the item unit of measure type's item to the last item added
    And the user sets the item unit of measure type's unit of measure type to EACH
    And the user sets the item unit of measure type's sort order to "1"
    And the user sets the item unit of measure type to be the default
    And the user adds the new item unit of measure type
    Then no error should occur
    When the user begins entering a new item alias
    And the user sets the item alias's item to the last item added
    And the user sets the item alias's unit of measure type to "EACH"
    And the user sets the item alias's item alias type to "UPC_E"
    And the user sets the item alias's alias to "0421000"
    And the user adds the new item alias
    Then the execution error IncorrectUpcELength should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a UPC-E item alias with an incorrect number system
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
    When the user begins entering a new item unit of measure type
    And the user sets the item unit of measure type's item to the last item added
    And the user sets the item unit of measure type's unit of measure type to EACH
    And the user sets the item unit of measure type's sort order to "1"
    And the user sets the item unit of measure type to be the default
    And the user adds the new item unit of measure type
    Then no error should occur
    When the user begins entering a new item alias
    And the user sets the item alias's item to the last item added
    And the user sets the item alias's unit of measure type to "EACH"
    And the user sets the item alias's item alias type to "UPC_E"
    And the user sets the item alias's alias to "04210005"
    And the user adds the new item alias
    Then the execution error IncorrectUpcENumberSystem should occur
    When the user begins entering a new item alias
    And the user sets the item alias's item to the last item added
    And the user sets the item alias's unit of measure type to "EACH"
    And the user sets the item alias's item alias type to "UPC_E"
    And the user sets the item alias's alias to "81234569"
    And the user adds the new item alias
    Then the execution error IncorrectUpcENumberSystem should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur
