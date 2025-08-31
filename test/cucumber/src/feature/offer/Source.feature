Feature: Employee source
  An employee wants to add, edit, and delete a source

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a source use, edits it, and deletes it
    Given the employee Test begins using the application
    When the user begins entering a new offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user sets the offer to not be the default
    And the user sets the offer's sort order to "10"
    And the user sets the offer's description to "Cucumber Offer"
    And the user adds the new offer
    Then no error should occur
    When the user begins entering a new use type
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user sets the use type to not be the default
    And the user sets the use type's sort order to "10"
    And the user sets the use type's description to "Cucumber Use Type"
    And the user adds the new use type
    Then no error should occur
    When the user begins entering a new use
    And the user sets the use's use name to "CUCUMBER_USE"
    And the user sets the use's use type name to "CUCUMBER_USE_TYPE"
    And the user sets the use to not be the default
    And the user sets the use's sort order to "10"
    And the user sets the use's description to "Cucumber Use"
    And the user adds the new use
    Then no error should occur
    When the user begins entering a new offer use
    And the user sets the offer use's offer name to the last offer added
    And the user sets the offer use's use name to the last use added
    And the user adds the new offer use
    Then no error should occur
    When the user begins entering a new source
    And the user sets the source's offer name to the last offer added
    And the user sets the source's use name to the last use added
    And the user sets the source's source name to "CUCUMBER_SOURCE"
    And the user sets the source to not be the default
    And the user sets the source's sort order to "10"
    And the user adds the new source
    Then no error should occur
    When the user begins specifying a source to edit
    And the user sets the source's source name to "CUCUMBER_SOURCE"
    When the user begins editing the source
    Then no error should occur
    And the user sets the source's sort order to "20"
    And the user finishes editing the source
    Then no error should occur
    When the user begins deleting a source
    And the user sets the source's source name to the last source added
    And the user deletes the source
    When the user begins deleting an offer use
    And the user sets the offer use's offer name to the last offer added
    And the user sets the offer use's use name to the last use added
    And the user deletes the offer use
    Then no error should occur
    When the user begins deleting an use
    And the user sets the use's use name to "CUCUMBER_USE"
    And the user deletes the use
    Then no error should occur
    When the user begins deleting an use type
    And the user sets the use type's use type name to "CUCUMBER_USE_TYPE"
    And the user deletes the use type
    Then no error should occur
    When the user begins deleting an offer
    And the user sets the offer's offer name to "CUCUMBER_OFFER"
    And the user deletes the offer
    Then no error should occur
