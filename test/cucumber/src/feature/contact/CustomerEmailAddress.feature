Feature: Customer email address
  A customer wants to add, edit and delete email addresses associated with their account

  Background:
    Given the customer Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "password"
    And the customer logs in
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does not allow solicitations
    Given the customer Test begins using the application
    When the user begins entering a new email address
    And the user sets the email address's email address to "Additional@echothree.com"
    And the user does not allow solicitations to the email address
    And the user adds the new email address
    Then no error should occur
    And the user deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does allow solicitations
    Given the customer Test begins using the application
    When the user begins entering a new email address
    And the user sets the email address's email address to "Additional@echothree.com"
    And the user does allow solicitations to the email address
    And the user adds the new email address
    Then no error should occur
    And the user deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does not allow solicitations
    Given the customer Test begins using the application
    When the user begins entering a new email address
    And the user sets the email address's email address to "Additional@echothree.com"
    And the user sets the email address's description to "Additional Email"
    And the user does not allow solicitations to the email address
    And the user adds the new email address
    Then no error should occur
    And the user deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does allow solicitations
    Given the customer Test begins using the application
    When the user begins entering a new email address
    And the user sets the email address's email address to "Additional@echothree.com"
    And the user sets the email address's description to "Additional Email"
    And the user does allow solicitations to the email address
    And the user adds the new email address
    Then no error should occur
    And the user deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes an email address with a description and does allow solicitations
    Given the customer Test begins using the application
    When the user begins entering a new email address
    And the user sets the email address's email address to "Additional@echothree.com"
    And the user sets the email address's description to "Additional Email"
    And the user does allow solicitations to the email address
    And the user adds the new email address
    Then no error should occur
    When the user begins specifying an email address to edit
    And the user sets the email address's contact mechanism to the last email address added
    When the user begins editing the email address
    Then no error should occur
    And the user sets the email address's email address to "Edited@echothree.com"
    And the user sets the email address's description to "Edited Email"
    And the user does not allow solicitations to the email address
    And the user finishes editing the email address
    Then no error should occur
    And the user deletes the last email address added
    Then no error should occur
