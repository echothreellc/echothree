Feature: Employee offer use
  An employee wants to add, edit, and delete an offer use

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an offer use, edits it, and deletes it
    Given the employee Test begins using the application
    And the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    And the user begins entering a new use type
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user sets the use type to not be the default
    And the user sets the use type's sort order to "10"
    And the user sets the use type's description to "Cucumber Use Type"
    And the user adds the new use type
    Then no error should occur
    And the user begins entering a new use
    And the user sets the use's use name to "CUCUMBER_USE"
    And the user sets the use's use type name to "CUCUMBER_USE_TYPE"
    And the user sets the use to not be the default
    And the user sets the use's sort order to "10"
    And the user sets the use's description to "Cucumber Use"
    And the user adds the new use
    Then no error should occur
    And the user begins entering a new offer use
    And the user sets the offer use's offer name to the last offer added
    And the user sets the offer use's use name to the last use added
    And the user adds the new offer use
    Then no error should occur
    And the user begins specifying an offer use to edit
    And the user sets the offer use's offer name to the last offer added
    And the user sets the offer use's use name to the last use added
    And the user begins editing the offer use
    Then no error should occur
    And the user sets the offer use's sales order sequence name to "DEFAULT"
    And the user finishes editing the offer use
    Then no error should occur
    And the user begins deleting an offer use
    And the user sets the offer use's offer name to the last offer added
    And the user sets the offer use's use name to the last use added
    And the user deletes the offer use
    Then no error should occur
    And the user begins deleting an use
    And the user sets the use's use name to "CUCUMBER_USE"
    And the user deletes the use
    Then no error should occur
    And the user begins deleting an use type
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user deletes the use type
    Then no error should occur
    And the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur
