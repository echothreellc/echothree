Feature: Customer email address
  A customer wants to add and remove email addresses associated with their account

  Scenario: Existing customer adds and then deletes an email address without a description and does not allow solicitations
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    And the customer Test adds the email address "Additional@echothree.com" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address without a description and does allow solicitations
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    And the customer Test adds the email address "Additional@echothree.com" and does allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does not allow solicitations
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    And the customer Test adds the email address "Additional@echothree.com" with the description "Additional Email" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an email address with a description and does allow solicitations
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    And the customer Test adds the email address "Additional@echothree.com" with the description "Additional Email" and does allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last email address added
    Then no error should occur
