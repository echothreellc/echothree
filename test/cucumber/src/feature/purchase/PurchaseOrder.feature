Feature: Employee purchase order
  An employee wants to add a purchase order

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a purchase order and sets the status of it to canceled
    Given the employee Test begins using the application
    When the user begins entering a new purchase order
    And the user sets the purchase order's vendor name to TEST_VENDOR
    And the user adds the new purchase order
    Then no error should occur
    When the user begins setting the status of a purchase order
    And the user sets the purchase order's purchase order name to the last purchase order added
    And the user sets the purchase order's status to ENTRY_TO_CANCELED
    And the user sets the status of the purchase order
    Then no error should occur

  Scenario: Existing employee adds a purchase order and sets the status of it to entry complete
    Given the employee Test begins using the application
    When the user begins entering a new purchase order
    And the user sets the purchase order's vendor name to TEST_VENDOR
    And the user adds the new purchase order
    Then no error should occur
    When the user begins setting the status of a purchase order
    And the user sets the purchase order's purchase order name to the last purchase order added
    And the user sets the purchase order's status to ENTRY_TO_ENTRY_COMPLETE
    And the user sets the status of the purchase order
    Then no error should occur
