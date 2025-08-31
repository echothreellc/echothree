Feature: Employee vendor item cost
  An employee wants to add, edit, and delete a vendor item cost

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a vendor, then a vendor item, then a vendor item cost, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new vendor
    And the user sets the vendor to use use item purchasing categories
    And the user indicates the vendor does allow solicitations to the email address
    And the user adds the new vendor
    Then no error should occur
    When the user begins entering a new vendor item
    And the user sets the vendor item's item name to minimal
    And the user sets the vendor item's vendor name to the last vendor added
    And the user sets the vendor item's vendor item name to vend-minimal
    And the user sets the vendor item's priority to "1"
    And the user adds the new vendor item
    Then no error should occur
    When the user begins entering a new vendor item cost
    And the user sets the vendor item cost's vendor name to the last vendor added
    And the user sets the vendor item cost's vendor item name to vend-minimal
    And the user sets the vendor item cost's inventory condition name to GOOD
    And the user sets the vendor item cost's unit of measure type name to EACH
    And the user sets the vendor item cost's unit cost to 1.99
    And the user adds the new vendor item cost
    Then no error should occur
    When the user begins specifying a vendor item cost to edit
    And the user sets the vendor item cost's vendor name to the last vendor added
    And the user sets the vendor item cost's vendor item name to vend-minimal
    And the user sets the vendor item cost's inventory condition name to GOOD
    And the user sets the vendor item cost's unit of measure type name to EACH
    When the user begins editing the vendor item cost
    Then no error should occur
    And the user sets the vendor item cost's unit cost to 2.99
    And the user finishes editing the vendor item cost
    Then no error should occur
    When the user begins deleting a vendor item cost
    And the user sets the vendor item cost's vendor name to the last vendor added
    And the user sets the vendor item cost's vendor item name to vend-minimal
    And the user sets the vendor item cost's inventory condition name to GOOD
    And the user sets the vendor item cost's unit of measure type name to EACH
    And the user deletes the vendor item cost
    Then no error should occur
    And the user sets the status of the last vendor added to ACTIVE_TO_INACTIVE
    Then no error should occur
