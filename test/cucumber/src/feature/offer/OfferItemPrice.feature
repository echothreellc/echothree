Feature: Employee offer item
  An employee wants to add, edit, and delete an offer item

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an offer item, and deletes it
    Given the employee Test begins using the application
    And the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    And the user begins entering a new offer item
    And the user sets the offer item's offer name to the last offer added
    And the user sets the offer item's item name to "MINIMAL"
    And the user adds the new offer item
    Then no error should occur
    And the user begins entering a new offer item price
    And the user sets the offer item price's offer name to the last offer added
    And the user sets the offer item price's item name to "MINIMAL"
    And the user sets the offer item price's inventory condition to "GOOD"
    And the user sets the offer item price's unit of measure type to "EACH"
    And the user sets the offer item price's currency to "USD"
    And the user sets the offer item price's unit price to "5.99"
    And the user adds the new offer item price
    Then no error should occur
    And the user begins specifying an offer item price to edit
    And the user sets the offer item price's offer name to the last offer added
    And the user sets the offer item price's item name to "MINIMAL"
    And the user sets the offer item price's inventory condition to "GOOD"
    And the user sets the offer item price's unit of measure type to "EACH"
    And the user sets the offer item price's currency to "USD"
    And the user begins editing the offer item price
    Then no error should occur
    And the user sets the offer item price's unit price to "7.99"
    And the user finishes editing the offer item price
    Then no error should occur
    And the user begins deleting an offer item price
    And the user sets the offer item price's offer name to the last offer added
    And the user sets the offer item price's item name to "MINIMAL"
    And the user sets the offer item price's inventory condition to "GOOD"
    And the user sets the offer item price's unit of measure type to "EACH"
    And the user sets the offer item price's currency to "USD"
    And the user deletes the offer item price
    Then no error should occur
    And the user begins deleting an offer item
    And the user sets the offer item's offer name to the last offer added
    And the user sets the offer item's item name to "MINIMAL"
    And the user deletes the offer item
    Then no error should occur
    And the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur
