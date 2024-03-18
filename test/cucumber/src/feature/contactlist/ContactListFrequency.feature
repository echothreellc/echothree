Feature: Employee contact list frequency
  An employee wants to add, edit, and delete a contact list frequency

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a contact list frequency, edits it, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new contact list frequency
    And the user sets the contact list frequency's contact list frequency name to "CUCUMBER_CONTACT_LIST_FREQUENCY"
    And the user sets the contact list frequency to not be the default
    And the user sets the contact list frequency's sort order to "10"
    And the user sets the contact list frequency's description to "Cucumber Contact List Group"
    And the user adds the new contact list frequency
    Then no error should occur
    When the user begins specifying a contact list frequency to edit
    And the user sets the contact list frequency's contact list frequency name to "CUCUMBER_CONTACT_LIST_FREQUENCY"
    And the user begins editing the contact list frequency
    Then no error should occur
    And the user sets the contact list frequency's sort order to "20"
    And the user finishes editing the contact list frequency
    Then no error should occur
    When the user begins deleting a contact list frequency
    And the user sets the contact list frequency's contact list frequency name to "CUCUMBER_CONTACT_LIST_FREQUENCY"
    And the user deletes the contact list frequency
    Then no error should occur
