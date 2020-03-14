Feature: Customer postal address
  A customer wants to add, edit and delete postal addresses associated with their account

  Background:
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes an postal address without a description and does not allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new postal address
    And the customer Test sets the postal address's first name to "Test"
    And the customer Test sets the postal address's last name to "Customer"
    And the customer Test sets the postal address's line 1 to "256 N Test Street"
    And the customer Test sets the postal address's city to "Des Moines"
    And the customer Test sets the postal address's state to "IA"
    And the customer Test sets the postal address's postal code to "50310"
    And the customer Test sets the postal address's country to "US"
    And the customer Test's postal address is not a commercial location
    And the customer Test does not allow solicitations to the postal address
    And the customer Test adds the new postal address
    Then no error should occur
    And the customer Test deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an postal address without a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new postal address
    And the customer Test sets the postal address's first name to "Test"
    And the customer Test sets the postal address's last name to "Customer"
    And the customer Test sets the postal address's line 1 to "256 N Test Street"
    And the customer Test sets the postal address's city to "Des Moines"
    And the customer Test sets the postal address's state to "IA"
    And the customer Test sets the postal address's postal code to "50310"
    And the customer Test sets the postal address's country to "US"
    And the customer Test's postal address is not a commercial location
    And the customer Test does allow solicitations to the postal address
    And the customer Test adds the new postal address
    Then no error should occur
    And the customer Test deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an postal address with a description and does not allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new postal address
    And the customer Test sets the postal address's first name to "Test"
    And the customer Test sets the postal address's last name to "Customer"
    And the customer Test sets the postal address's line 1 to "256 N Test Street"
    And the customer Test sets the postal address's city to "Des Moines"
    And the customer Test sets the postal address's state to "IA"
    And the customer Test sets the postal address's postal code to "50310"
    And the customer Test sets the postal address's country to "US"
    And the customer Test sets the postal address's description to "Additional Postal"
    And the customer Test's postal address is not a commercial location
    And the customer Test does not allow solicitations to the postal address
    And the customer Test adds the new postal address
    Then no error should occur
    And the customer Test deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an postal address with a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new postal address
    And the customer Test sets the postal address's first name to "Test"
    And the customer Test sets the postal address's last name to "Customer"
    And the customer Test sets the postal address's line 1 to "256 N Test Street"
    And the customer Test sets the postal address's city to "Des Moines"
    And the customer Test sets the postal address's state to "IA"
    And the customer Test sets the postal address's postal code to "50310"
    And the customer Test sets the postal address's country to "US"
    And the customer Test sets the postal address's description to "Additional Postal"
    And the customer Test's postal address is not a commercial location
    And the customer Test does allow solicitations to the postal address
    And the customer Test adds the new postal address
    Then no error should occur
    And the customer Test deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes an postal address with a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new postal address
    And the customer Test sets the postal address's first name to "Test"
    And the customer Test sets the postal address's last name to "Customer"
    And the customer Test sets the postal address's line 1 to "256 N Test Street"
    And the customer Test sets the postal address's city to "Des Moines"
    And the customer Test sets the postal address's state to "IA"
    And the customer Test sets the postal address's postal code to "50310"
    And the customer Test sets the postal address's country to "US"
    And the customer Test sets the postal address's description to "Additional Postal"
    And the customer Test's postal address is not a commercial location
    And the customer Test does not allow solicitations to the postal address
    And the customer Test adds the new postal address
    Then no error should occur
    And the customer Test begins editing the last postal address added
    Then no error should occur
    And the customer Test sets the postal address's line 1 to "128 N Test Street"
    And the customer Test sets the postal address's description to "Edited Postal"
    And the customer Test finishes editing the postal address
    Then no error should occur
    And the customer Test deletes the last postal address added
    Then no error should occur
