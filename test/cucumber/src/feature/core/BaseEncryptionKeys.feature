Feature: Base encryption keys
  An employee wants load and change the base encryption keys

  Background:
    Given the employee Test is not currently logged in
    When the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee loads the existing base encryption keys
    Given the employee Test is currently logged in
    And the employee Test loads the existing base encryption keys
    Then no error should occur

  Scenario: Existing employee changes the base encryption keys
    Given the employee Test is currently logged in
    And the employee Test loads the existing base encryption keys
    Then no error should occur
    And the employee Test changes the base encryption keys
    Then no error should occur
