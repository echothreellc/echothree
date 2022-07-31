Feature: Employee sales order batch
  An employee wants to add and delete a sales order batch

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds and delete a sales order batch
    Given the employee Test begins using the application
    And the user adds a new sales order batch with the currency USD and payment method VISA
    Then no error should occur
    And the user deletes the last sales order batch added
    Then no error should occur
