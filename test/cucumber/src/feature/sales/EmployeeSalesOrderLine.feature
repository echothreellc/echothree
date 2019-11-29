Feature: Employee sales order line
  An employee wants to add a line to a sales order

  Background:
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a sales order line to existing order with defaults
    Given the employee Test is currently logged in
    And the employee Test adds a new sales order
    Then no error should occur
    And the employee Test adds 1 minimal to the sales order
    Then no error should occur

  Scenario: Existing employee adds a sales order line to new order with defaults
    Given the employee Test is currently logged in
    And the employee Test adds 1 minimal to a new sales order
    Then no error should occur
