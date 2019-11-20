Feature: Customer web address
  A customer wants to add, edit and delete web addresses associated with their account

  Background:
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no customer error should occur

  Scenario: Existing customer adds and then deletes an web address without a description
    Given the customer Test is currently logged in
    And the customer Test adds the web address "http://www.echothree.com/"
    Then no customer error should occur
    And the customer Test deletes the last web address added
    Then no customer error should occur

  Scenario: Existing customer adds and then deletes an web address with a description
    Given the customer Test is currently logged in
    And the customer Test adds the web address "http://www.echothree.com/" with the description "Additional Web Address"
    Then no customer error should occur
    And the customer Test deletes the last web address added
    Then no customer error should occur

  Scenario: Existing customer adds, edits and then deletes an web address with a description
    Given the customer Test is currently logged in
    And the customer Test adds the web address "http://www.echothree.com/" with the description "Additional Web Address"
    Then no customer error should occur
    And the customer Test modifies the last web address added to "http://www.echothree.com/~rich" with the description "Edited Email"
    Then no customer error should occur
    And the customer Test deletes the last web address added
    Then no customer error should occur
