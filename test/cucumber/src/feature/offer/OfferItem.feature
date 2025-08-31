Feature: Employee offer item
  An employee wants to add and edit an offer item

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an offer item, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    When the user begins entering a new offer item
    And the user sets the offer item's offer name to the last offer added
    And the user sets the offer item's item name to "MINIMAL"
    And the user adds the new offer item
    Then no error should occur
    When the user begins deleting an offer item
    And the user sets the offer item's offer name to the last offer added
    And the user sets the offer item's item name to "MINIMAL"
    And the user deletes the offer item
    Then no error should occur
    When the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur

  Scenario: Existing employee adds an offer with an offer item selector and attempts to create an offer item
    Given the employee Test begins using the application
    When the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer's offer item selector name to "EXAMPLE_OFFER_ITEM_1"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    When the user begins entering a new offer item
    And the user sets the offer item's offer name to the last offer added
    And the user sets the offer item's item name to "MINIMAL"
    And the user adds the new offer item
    Then the execution error CannotManuallyCreateOfferItemWhenOfferItemSelectorSet should occur
    When the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur

  Scenario: Existing employee adds an offer with an offer item selector and attempts to delete an offer item
    Given the employee Test begins using the application
    When the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer's offer item selector name to "EXAMPLE_OFFER_ITEM_1"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    When the user begins deleting an offer item
    And the user sets the offer item's offer name to the last offer added
    And the user sets the offer item's item name to "MINIMAL"
    And the user deletes the offer item
    Then the execution error CannotManuallyDeleteOfferItemWhenOfferItemSelectorSet should occur
    When the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur
