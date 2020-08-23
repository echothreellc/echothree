Feature: Employee offer
  An employee wants to add, edit, and delete an offer

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an offer, edits it, and deletes it
    Given the employee Test begins using the application
    And the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    And the user begins specifying an offer to edit
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user begins editing the offer
    Then no error should occur
    And the user sets the offer's sort order to "20"
    And the user finishes editing the offer
    Then no error should occur
    And the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur
