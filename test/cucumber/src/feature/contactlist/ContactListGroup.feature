Feature: Employee contact list group
  An employee wants to add, edit, and delete a contact list group

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a contact list group, edits it, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new contact list group
    And the user sets the contact list group's contact list group name to "CUCUMBER_CONTACT_LIST_GROUP"
    And the user sets the contact list group to not be the default
    And the user sets the contact list group's sort order to "10"
    And the user sets the contact list group's description to "Cucumber Contact List Group"
    And the user adds the new contact list group
    Then no error should occur
    When the user begins specifying a contact list group to edit
    And the user sets the contact list group's contact list group name to "CUCUMBER_CONTACT_LIST_GROUP"
    And the user begins editing the contact list group
    Then no error should occur
    And the user sets the contact list group's sort order to "20"
    And the user finishes editing the contact list group
    Then no error should occur
    When the user begins deleting a contact list group
    And the user sets the contact list group's contact list group name to "CUCUMBER_CONTACT_LIST_GROUP"
    And the user deletes the contact list group
    Then no error should occur
