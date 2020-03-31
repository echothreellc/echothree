Feature: Employee sales order batch
  An employee wants to add and delete a sales order batch

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds and delete a sales order batch
    Given the employee Test begins using the application
    And the employee Test adds a new sales order batch with the currency USD and payment method VISA
    Then no error should occur
    And the employee Test deletes the last sales order batch added
    Then no error should occur
