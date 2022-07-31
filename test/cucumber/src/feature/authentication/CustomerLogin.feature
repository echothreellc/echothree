Feature: Customer login
  A customer wants to login so that they will be able to work with their account

  Scenario: Existing customer login with remote IPv4 address that's successful
    Given the customer Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "password"
    And the customer sets the remote IPv4 address to "0.0.0.0"
    And the customer logs in
    Then no error should occur

  Scenario: Existing customer login with remote IPv4 address that fails
    Given the customer Test begins using the application
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "not-my-password"
    And the customer sets the remote IPv4 address to "0.0.0.0"
    And the customer logs in
    Then an error should occur

  Scenario: Existing customer login that's successful
    Given the customer Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "password"
    And the customer logs in
    Then no error should occur

  Scenario: Existing customer login that fails
    Given the customer Test begins using the application
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "not-my-password"
    And the customer logs in
    Then an error should occur
