Feature: Employee sales order
  An employee wants to add a sales order

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a sales order with defaults
    Given the employee Test begins using the application
    And the user adds a new sales order
    Then no error should occur
