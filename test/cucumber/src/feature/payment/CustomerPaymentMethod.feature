Feature: Party payment methods
  A customer wants to maintain their credit cards

  Background:
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur
    And the employee Test loads the existing base encryption keys
    Then no error should occur
    And the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes a credit card
    Given the customer Test is currently logged in
    And the customer Test begins entering a new payment method
    And the customer Test sets the payment method to VISA
    And the customer Test sets the payment method's description to "My Card"
    And the customer Test sets the payment method to be deleted when unused
    And the customer Test sets the payment method to be the default
    And the customer Test sets the payment method's sort order to "1"
    And the customer Test sets the payment method's number to "4000300020001000"
    And the customer Test sets the payment method's security code to "123"
    And the customer Test sets the payment method's expiration month to "3"
    And the customer Test sets the payment method's expiration year to "2030"
    And the customer Test sets the payment method's first name to "Test"
    And the customer Test sets the payment method's last name to "Customer"
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
    And the customer Test sets the payment method's billing contact to the last postal address added
    And the customer Test adds the new payment method
    Then no error should occur
    And the customer Test deletes the last payment method added
    Then no error should occur
    And the customer Test deletes the last postal address added
    Then no error should occur
