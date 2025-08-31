Feature: Employee allocation priority
  An employee wants to add, edit, and delete an allocation priority

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds an allocation priority, edits it, changes its status, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new allocation priority
    And the user sets the allocation priority's allocation priority name to "CucumberAllocationPriority"
    And the user sets the allocation priority's priority to "25"
    And the user sets the allocation priority to be the default
    And the user sets the allocation priority's sort order to "10"
    And the user sets the allocation priority's description to "Cucumber Allocation Priority"
    And the user adds the new allocation priority
    Then no error should occur
    When the user begins specifying an allocation priority to edit
    And the user sets the allocation priority's allocation priority name to the last allocation priority added
    When the user begins editing the allocation priority
    Then no error should occur
    And the user sets the allocation priority's priority to "35"
    And the user sets the allocation priority's sort order to "20"
    And the user sets the allocation priority's description to "Edited Cucumber Allocation Priority"
    And the user finishes editing the allocation priority
    Then no error should occur
    When the user begins deleting an allocation priority
    And the user sets the allocation priority's allocation priority name to the last allocation priority added
    And the user deletes the allocation priority
    Then no error should occur
