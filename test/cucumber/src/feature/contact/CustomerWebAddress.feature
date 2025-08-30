Feature: Customer web address
  A customer wants to add, edit and delete web addresses associated with their account

  Background:
    Given the customer Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an customer
    And the customer sets the username to "TestC@echothree.com"
    And the customer sets the password to "password"
    And the customer logs in
    Then no error should occur

  Scenario: Existing customer adds and then deletes an web address without a description
    Given the customer Test begins using the application
    When the user begins entering a new web address
    And the user sets the web address's url to "http://www.echothree.com/"
    And the user adds the new web address
    Then no error should occur
    And the user deletes the last web address added
    Then no error should occur

  Scenario: Existing customer adds and then deletes an web address with a description
    Given the customer Test begins using the application
    When the user begins entering a new web address
    And the user sets the web address's url to "http://www.echothree.com/"
    And the user sets the web address's description to "Additional Web Address"
    And the user adds the new web address
    Then no error should occur
    And the user deletes the last web address added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes an web address with a description
    Given the customer Test begins using the application
    When the user begins entering a new web address
    And the user sets the web address's url to "http://www.echothree.com/"
    And the user sets the web address's description to "Additional Web Address"
    And the user adds the new web address
    Then no error should occur
    When the user begins specifying a web address to edit
    And the user sets the web address's contact mechanism to the last web address added
    When the user begins editing the web address
    Then no error should occur
    And the user sets the web address's url to "http://www.echothree.com/~rich"
    And the user sets the web address's description to "Edited Web Address"
    And the user finishes editing the web address
    Then no error should occur
    And the user deletes the last web address added
    Then no error should occur
