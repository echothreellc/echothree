Feature: Employee login
  A employee wants to login so that they will be able to work with their account

  Scenario: Existing employee login that's successful
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no employee error should occur

  Scenario: Existing employee login that fails
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "not-my-password" and company "TEST_COMPANY"
    Then an employee error should occur
