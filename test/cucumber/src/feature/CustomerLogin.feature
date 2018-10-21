Feature: Customer login
  A customer wants to login so that they will be able to work with their account

  Scenario: Existing customer login that's successful
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no customer login error should occur

  Scenario: Existing customer login that fails
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "not-my-password"
    Then a customer login error should occur
