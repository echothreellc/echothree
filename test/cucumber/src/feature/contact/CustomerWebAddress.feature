Feature: Customer web address
  A customer wants to add, edit and delete web addresses associated with their account

  Background:
    Given the customer Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes an web address without a description
    Given the customer Test is currently logged in
    And the customer Test begins entering a new web address
    And the customer Test sets the web address's url to "http://www.echothree.com/"
    And the customer Test adds the new web address
    Then no error should occur
    And the customer Test deletes the last web address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an web address with a description
    Given the customer Test is currently logged in
    And the customer Test begins entering a new web address
    And the customer Test sets the web address's url to "http://www.echothree.com/"
    And the customer Test sets the web address's description to "Additional Web Address"
    And the customer Test adds the new web address
    Then no error should occur
    And the customer Test deletes the last web address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes an web address with a description
    Given the customer Test is currently logged in
    And the customer Test begins entering a new web address
    And the customer Test sets the web address's url to "http://www.echothree.com/"
    And the customer Test sets the web address's description to "Additional Web Address"
    And the customer Test adds the new web address
    Then no error should occur
    And the customer Test begins editing the last web address added
    Then no error should occur
    And the customer Test sets the web address's url to "http://www.echothree.com/~rich"
    And the customer Test sets the web address's description to "Edited Web Address"
    And the customer Test finishes editing the web address
    Then no error should occur
    And the customer Test deletes the last web address added
    Then no error should occur
