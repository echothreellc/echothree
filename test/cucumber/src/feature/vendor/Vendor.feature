Feature: Employee item price
  An employee wants to add, edit, and delete a vendor

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a vendor, edits it, and sets the status of it
    Given the employee Test begins using the application
    And the user begins entering a new vendor
    And the user sets the vendor to use use item purchasing categories
    And the user indicates the vendor does allow solicitations to the email address
    And the user sets the vendor to not be taxable
    And the user adds the new vendor
    Then no error should occur
    And the user begins specifying a vendor to edit
    And the user sets the vendor's vendor name to the last vendor added
    And the user begins editing the vendor
    And the user sets the vendor's name to "Cucumber Vendor"
    And the user finishes editing the vendor
    And the user sets the status of the last vendor added to ACTIVE_TO_INACTIVE
    Then no error should occur
