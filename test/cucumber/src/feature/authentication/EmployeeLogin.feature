Feature: Employee login
  An employee wants to login so that they will be able to work with their account

  Scenario: Existing employee login that's successful
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee login that fails
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "not-my-password" and company "TEST_COMPANY"
    Then an error should occur
