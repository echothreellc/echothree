Feature: Employee sales order line
  An employee wants to add a line to a sales order

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a sales order line to existing order with defaults
    Given the employee Test begins using the application
    And the user adds a new sales order
    Then no error should occur
    And the user adds 1 minimal to the sales order
    Then no error should occur

  Scenario: Existing employee adds a sales order line to new order with defaults
    Given the employee Test begins using the application
    And the user adds 1 minimal to a new sales order
    Then no error should occur
