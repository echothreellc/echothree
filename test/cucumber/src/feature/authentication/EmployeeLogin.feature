Feature: Employee login
  An employee wants to login so that they will be able to work with their account

  Scenario: Existing employee login with remote IPv4 address that's successful
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee sets the remote IPv4 address to "0.0.0.0"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee login with remote IPv4 address that fails
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "not-my-password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee sets the remote IPv4 address to "0.0.0.0"
    And the employee logs in
    Then an error should occur

  Scenario: Existing employee login that's successful
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee login that fails
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "not-my-password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then an error should occur
