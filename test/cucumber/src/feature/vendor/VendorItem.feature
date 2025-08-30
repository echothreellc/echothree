Feature: Employee vendor item
  An employee wants to add, edit, and delete a vendor item

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a vendor, then a vendor item, edits it, and sets the status of it
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
    When the user begins specifying a vendor item to edit
    And the user sets the vendor item's vendor name to the last vendor added
    And the user sets the vendor item's vendor item name to vend-minimal
    When the user begins editing the vendor item
    Then no error should occur
    And the user sets the vendor item's description to "That Minimal Item"
    And the user finishes editing the vendor item
    Then no error should occur
    When the user begins setting the status of a vendor item
    And the user sets the vendor item's vendor name to the last vendor added
    And the user sets the vendor item's vendor item name to vend-minimal
    And the user sets the vendor item's status to ACTIVE_TO_INACTIVE
    And the user sets the status of the vendor item
    Then no error should occur
    When the user begins deleting a vendor item
    And the user sets the vendor item's vendor name to the last vendor added
    And the user sets the vendor item's vendor item name to vend-minimal
    And the user deletes the vendor item
    Then no error should occur
    And the user sets the status of the last vendor added to ACTIVE_TO_INACTIVE
    Then no error should occur
