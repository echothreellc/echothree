Feature: Party payment methods
  A customer wants to maintain their credit cards

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur
    And the user loads the existing base encryption keys
    Then no error should occur
    And the customer Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "password"
    And the customer logs in
    Then no error should occur

  Scenario: Existing customer adds and then deletes a credit card
    Given the customer Test begins using the application
    When the user begins entering a new payment method
    And the user sets the payment method to VISA
    And the user sets the payment method's description to "My Card"
    And the user sets the payment method to be deleted when unused
    And the user sets the payment method to be the default
    And the user sets the payment method's sort order to "1"
    And the user sets the payment method's number to "4000300020001000"
    And the user sets the payment method's security code to "123"
    And the user sets the payment method's expiration month to "3"
    And the user sets the payment method's expiration year to "2030"
    And the user sets the payment method's first name to "Test"
    And the user sets the payment method's last name to "Customer"
    When the user begins entering a new postal address
    And the user sets the postal address's first name to "Test"
    And the user sets the postal address's last name to "Customer"
    And the user sets the postal address's line 1 to "256 N Test Street"
    And the user sets the postal address's city to "Des Moines"
    And the user sets the postal address's state to "IA"
    And the user sets the postal address's postal code to "50310"
    And the user sets the postal address's country to "US"
    And the user's postal address is not a commercial location
    And the user does not allow solicitations to the postal address
    And the user adds the new postal address
    Then no error should occur
    And the user sets the payment method's billing contact to the last postal address added
    And the user adds the new payment method
    Then no error should occur
    And the user deletes the last payment method added
    Then no error should occur
    And the user deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer adds and attempts to delete the postal address before the credit card and then deletes a credit card
    Given the customer Test begins using the application
    When the user begins entering a new payment method
    And the user sets the payment method to VISA
    And the user sets the payment method's description to "My Card"
    And the user sets the payment method to be deleted when unused
    And the user sets the payment method to be the default
    And the user sets the payment method's sort order to "1"
    And the user sets the payment method's number to "4000300020001000"
    And the user sets the payment method's security code to "123"
    And the user sets the payment method's expiration month to "3"
    And the user sets the payment method's expiration year to "2030"
    And the user sets the payment method's first name to "Test"
    And the user sets the payment method's last name to "Customer"
    When the user begins entering a new postal address
    And the user sets the postal address's first name to "Test"
    And the user sets the postal address's last name to "Customer"
    And the user sets the postal address's line 1 to "256 N Test Street"
    And the user sets the postal address's city to "Des Moines"
    And the user sets the postal address's state to "IA"
    And the user sets the postal address's postal code to "50310"
    And the user sets the postal address's country to "US"
    And the user's postal address is not a commercial location
    And the user does not allow solicitations to the postal address
    And the user adds the new postal address
    Then no error should occur
    And the user sets the payment method's billing contact to the last postal address added
    And the user adds the new payment method
    Then no error should occur
    And the user deletes the last postal address added
    Then an error should occur
    And the user deletes the last payment method added
    Then no error should occur
    And the user deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer tries to add a credit card that has a bad number
    Given the customer Test begins using the application
    When the user begins entering a new payment method
    And the user sets the payment method to VISA
    And the user sets the payment method's description to "My Card"
    And the user sets the payment method to be deleted when unused
    And the user sets the payment method to be the default
    And the user sets the payment method's sort order to "1"
    And the user sets the payment method's number to "1111111111111111"
    And the user sets the payment method's security code to "123"
    And the user sets the payment method's expiration month to "3"
    And the user sets the payment method's expiration year to "2030"
    And the user sets the payment method's first name to "Test"
    And the user sets the payment method's last name to "Customer"
    When the user begins entering a new postal address
    And the user sets the postal address's first name to "Test"
    And the user sets the postal address's last name to "Customer"
    And the user sets the postal address's line 1 to "256 N Test Street"
    And the user sets the postal address's city to "Des Moines"
    And the user sets the postal address's state to "IA"
    And the user sets the postal address's postal code to "50310"
    And the user sets the postal address's country to "US"
    And the user's postal address is not a commercial location
    And the user does not allow solicitations to the postal address
    And the user adds the new postal address
    Then no error should occur
    And the user sets the payment method's billing contact to the last postal address added
    And the user adds the new payment method
    Then an error should occur
    And the user deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer tries to add a credit card that is expired
    Given the customer Test begins using the application
    When the user begins entering a new payment method
    And the user sets the payment method to VISA
    And the user sets the payment method's description to "My Card"
    And the user sets the payment method to be deleted when unused
    And the user sets the payment method to be the default
    And the user sets the payment method's sort order to "1"
    And the user sets the payment method's number to "4000300020001000"
    And the user sets the payment method's security code to "123"
    And the user sets the payment method's expiration month to "3"
    And the user sets the payment method's expiration year to "2010"
    And the user sets the payment method's first name to "Test"
    And the user sets the payment method's last name to "Customer"
    When the user begins entering a new postal address
    And the user sets the postal address's first name to "Test"
    And the user sets the postal address's last name to "Customer"
    And the user sets the postal address's line 1 to "256 N Test Street"
    And the user sets the postal address's city to "Des Moines"
    And the user sets the postal address's state to "IA"
    And the user sets the postal address's postal code to "50310"
    And the user sets the postal address's country to "US"
    And the user's postal address is not a commercial location
    And the user does not allow solicitations to the postal address
    And the user adds the new postal address
    Then no error should occur
    And the user sets the payment method's billing contact to the last postal address added
    And the user adds the new payment method
    Then an error should occur
    And the user deletes the last postal address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes a credit card
    Given the customer Test begins using the application
    When the user begins entering a new payment method
    And the user sets the payment method to VISA
    And the user sets the payment method's description to "My Card"
    And the user sets the payment method to be deleted when unused
    And the user sets the payment method to be the default
    And the user sets the payment method's sort order to "1"
    And the user sets the payment method's number to "4000300020001000"
    And the user sets the payment method's security code to "123"
    And the user sets the payment method's expiration month to "3"
    And the user sets the payment method's expiration year to "2030"
    And the user sets the payment method's first name to "Test"
    And the user sets the payment method's last name to "Customer"
    When the user begins entering a new postal address
    And the user sets the postal address's first name to "Test"
    And the user sets the postal address's last name to "Customer"
    And the user sets the postal address's line 1 to "256 N Test Street"
    And the user sets the postal address's city to "Des Moines"
    And the user sets the postal address's state to "IA"
    And the user sets the postal address's postal code to "50310"
    And the user sets the postal address's country to "US"
    And the user's postal address is not a commercial location
    And the user does not allow solicitations to the postal address
    And the user adds the new postal address
    Then no error should occur
    And the user sets the payment method's billing contact to the last postal address added
    And the user adds the new payment method
    Then no error should occur
    When the user begins editing the last payment method added
    And the user sets the payment method's description to "My Edited Card"
    And the user sets the payment method's number to "4444333322221111"
    And the user sets the payment method's security code to "456"
    And the user sets the payment method's expiration month to "4"
    And the user sets the payment method's expiration year to "2031"
    And the user finishes editing the payment method
    And the user deletes the last payment method added
    Then no error should occur
    And the user deletes the last postal address added
    Then no error should occur
