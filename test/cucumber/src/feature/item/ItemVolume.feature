Feature: Employee item volume
  An employee wants to add, edit, and delete an item volume

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds two item unit of measure type, edits one of them, and then deletes both
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
    When the user begins entering a new item volume
    And the user sets the item volume's item to the last item added
    And the user sets the item volume's unit of measure type to "EACH"
    And the user sets the item volume's item volume type to "EXAMPLE_ITEM_VOLUME_TYPE"
    And the user sets the item volume's height unit of measure type to "INCH"
    And the user sets the item volume's height to "4"
    And the user sets the item volume's width unit of measure type to "INCH"
    And the user sets the item volume's width to "8"
    And the user sets the item volume's depth unit of measure type to "INCH"
    And the user sets the item volume's depth to "12"
    And the user adds the new item volume
    Then no error should occur
    When the user begins specifying an item volume to edit
    And the user sets the item volume's item to the last item added
    And the user sets the item volume's unit of measure type to "EACH"
    And the user sets the item volume's item volume type to "EXAMPLE_ITEM_VOLUME_TYPE"
    When the user begins editing the item volume
    Then no error should occur
    And the user sets the item volume's height unit of measure type to "INCH"
    And the user sets the item volume's height to "9"
    And the user sets the item volume's width unit of measure type to "INCH"
    And the user sets the item volume's width to "16"
    And the user sets the item volume's depth unit of measure type to "INCH"
    And the user sets the item volume's depth to "24"
    When the user finishes editing the item volume
    Then no error should occur
    When the user begins deleting an item volume
    And the user sets the item volume's item to the last item added
    And the user sets the item volume's unit of measure type to "EACH"
    And the user sets the item volume's item volume type to "EXAMPLE_ITEM_VOLUME_TYPE"
    And the user deletes the item volume
    Then no error should occur
    And the user sets the status of the last item added to ACTIVE_TO_DISCONTINUED
    Then no error should occur
