Feature: Employee Bookland EAN item alias
  An employee wants to add, edit, and delete an item alias that is a Bookland EAN

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an item, then a valid Bookland EAN item alias with a 978 prefix, edits it, and deletes it
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
    And the user sets the item alias's item alias type to "BOOKLAND_EAN"
    And the user sets the item alias's alias to "9780306406157"
    And the user adds the new item alias
    Then no error should occur
    When the user begins deleting an item alias
    And the user sets the item alias's alias to "9780306406157"
    And the user deletes the item alias
    Then no error should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then a valid Bookland EAN item alias with a 979 prefix, edits it, and deletes it
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
    And the user sets the item alias's item alias type to "BOOKLAND_EAN"
    And the user sets the item alias's alias to "9791234567896"
    And the user adds the new item alias
    Then no error should occur
    When the user begins deleting an item alias
    And the user sets the item alias's alias to "9791234567896"
    And the user deletes the item alias
    Then no error should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a Bookland EAN item alias with an invalid prefix
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
    And the user sets the item alias's item alias type to "BOOKLAND_EAN"
    And the user sets the item alias's alias to "9770306406157"
    And the user adds the new item alias
    Then the execution error IncorrectBooklandEanPrefix should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a Bookland EAN item alias with an incorrect checksum
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
    And the user sets the item alias's item alias type to "BOOKLAND_EAN"
    And the user sets the item alias's alias to "9780306406158"
    And the user adds the new item alias
    Then the execution error IncorrectEan13Checksum should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a Bookland EAN item alias with an incorrect character
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
    And the user sets the item alias's item alias type to "BOOKLAND_EAN"
    And the user sets the item alias's alias to "978030640615X"
    And the user adds the new item alias
    Then the execution error IncorrectBooklandEanCharacter should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur

  Scenario: Existing employee adds an item, then tries to add a Bookland EAN item alias with an incorrect length
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
    And the user sets the item alias's item alias type to "BOOKLAND_EAN"
    And the user sets the item alias's alias to "978030640615"
    And the user adds the new item alias
    Then the execution error IncorrectBooklandEanLength should occur
    When the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur
