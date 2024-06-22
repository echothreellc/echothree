Feature: Base encryption keys
  An employee wants load and change the base encryption keys

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee loads the existing base encryption keys
    Given the employee Test begins using the application
    And the user loads the existing base encryption keys
    Then no error should occur

  Scenario: Existing employee changes the base encryption keys
    Given the employee Test begins using the application
    And the user loads the existing base encryption keys
    Then no error should occur
    And the user changes the base encryption keys
    Then no error should occur
