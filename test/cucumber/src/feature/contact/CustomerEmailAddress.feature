Feature: Customer email address
  A customer wants to add, edit and delete email addresses associated with their account

  Background:
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does not allow solicitations
    Given the customer Test is currently logged in
    And the customer Test adds the email address "Additional@echothree.com" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test adds the email address "Additional@echothree.com" and does allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does not allow solicitations
    Given the customer Test is currently logged in
    And the customer Test adds the email address "Additional@echothree.com" with the description "Additional Email" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test adds the email address "Additional@echothree.com" with the description "Additional Email" and does allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes an email address with a description and does allow solicitations
    Given the customer Test is currently logged in
    And the customer Test adds the email address "Additional@echothree.com" with the description "Additional Email" and does allow solicitations to it
    Then no error should occur
    And the customer Test modifies the last email address added to "Edited@echothree.com" with the description "Edited Email" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur
