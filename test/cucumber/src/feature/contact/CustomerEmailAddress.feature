Feature: Customer email address
  A customer wants to add, edit and delete email addresses associated with their account

  Background:
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does not allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new email address
    And the customer Test sets the email address's email address to "Additional@echothree.com"
    And the customer Test does not allow solicitations to the email address
    And the customer Test adds the new email address
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new email address
    And the customer Test sets the email address's email address to "Additional@echothree.com"
    And the customer Test does allow solicitations to the email address
    And the customer Test adds the new email address
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does not allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new email address
    And the customer Test sets the email address's email address to "Additional@echothree.com"
    And the customer Test sets the email address's description to "Additional Email"
    And the customer Test does not allow solicitations to the email address
    And the customer Test adds the new email address
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new email address
    And the customer Test sets the email address's email address to "Additional@echothree.com"
    And the customer Test sets the email address's description to "Additional Email"
    And the customer Test does allow solicitations to the email address
    And the customer Test adds the new email address
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes an email address with a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test begins entering a new email address
    And the customer Test sets the email address's email address to "Additional@echothree.com"
    And the customer Test sets the email address's description to "Additional Email"
    And the customer Test does allow solicitations to the email address
    And the customer Test adds the new email address
    Then no error should occur
    And the customer Test begins editing the last email address added
    Then no error should occur
    And the customer Test sets the email address's email address to "Edited@echothree.com"
    And the customer Test sets the email address's description to "Edited Email"
    And the customer Test does not allow solicitations to the email address
    And the customer Test finishes editing the email address
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur
