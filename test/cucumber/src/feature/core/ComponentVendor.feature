Feature: Employee component vendor
  An employee wants to add, edit, and delete a component vendor

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a component vendor, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new component vendor
    And the user sets the component vendor's name to TEST_COMPONENT_VENDOR
    And the user sets the component vendor's description to "Test Component Vendor"
    And the user adds the new component vendor
    Then no error should occur
    When the user begins specifying a component vendor to edit
    And the user sets the component vendor's name to the last component vendor added
    When the user begins editing the component vendor
    Then no error should occur
    When the user sets the component vendor's description to "Test Edited Component Vendor"
    And the user finishes editing the component vendor
    Then no error should occur
    When the user begins deleting a component vendor
    And the user sets the component vendor's name to the last component vendor added
    And the user deletes the component vendor
    Then no error should occur
