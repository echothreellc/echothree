Feature: Customer login
  A customer wants to login so that they will be able to work with their account

  Scenario: Existing customer login that's successful
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer login that fails
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "not-my-password"
    Then an error should occur
