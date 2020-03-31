Feature: Customer telephone
  A customer wants to add, edit and delete telephones associated with their account

  Background:
    Given the customer Test begins using the application
    And the user is not currently logged in
    When the user logs in as a customer with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone without a description and does not allow solicitations
    Given the customer Test begins using the application
    And the customer Test begins entering a new telephone number
    And the customer Test sets the telephone number's country to "US"
    And the customer Test sets the telephone number's area code to "515"
    And the customer Test sets the telephone number's number to "555-1212"
    And the customer Test sets the telephone number's extension to "100"
    And the customer Test does not allow solicitations to the telephone number
    And the customer Test adds the new telephone number
    Then no error should occur
    And the customer Test deletes the last telephone number added
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone without a description and does allow solicitations
    Given the customer Test begins using the application
    And the customer Test begins entering a new telephone number
    And the customer Test sets the telephone number's country to "US"
    And the customer Test sets the telephone number's area code to "515"
    And the customer Test sets the telephone number's number to "555-1212"
    And the customer Test sets the telephone number's extension to "100"
    And the customer Test does allow solicitations to the telephone number
    And the customer Test adds the new telephone number
    Then no error should occur
    And the customer Test deletes the last telephone number added
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone with a description and does not allow solicitations
    Given the customer Test begins using the application
    And the customer Test begins entering a new telephone number
    And the customer Test sets the telephone number's country to "US"
    And the customer Test sets the telephone number's area code to "515"
    And the customer Test sets the telephone number's number to "555-1212"
    And the customer Test sets the telephone number's extension to "100"
    And the customer Test sets the telephone number's description to "Additional Telephone"
    And the customer Test does not allow solicitations to the telephone number
    And the customer Test adds the new telephone number
    Then no error should occur
    And the customer Test deletes the last telephone number added
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone with a description and does allow solicitations
    Given the customer Test begins using the application
    And the customer Test begins entering a new telephone number
    And the customer Test sets the telephone number's country to "US"
    And the customer Test sets the telephone number's area code to "515"
    And the customer Test sets the telephone number's number to "555-1212"
    And the customer Test sets the telephone number's extension to "100"
    And the customer Test sets the telephone number's description to "Additional Telephone"
    And the customer Test does allow solicitations to the telephone number
    And the customer Test adds the new telephone number
    Then no error should occur
    And the customer Test deletes the last telephone number added
    Then no error should occur

  Scenario: Existing customer adds, edits and then deletes a telephone with a description and does allow solicitations
    Given the customer Test begins using the application
    And the customer Test begins entering a new telephone number
    And the customer Test sets the telephone number's country to "US"
    And the customer Test sets the telephone number's area code to "515"
    And the customer Test sets the telephone number's number to "555-1212"
    And the customer Test sets the telephone number's extension to "100"
    And the customer Test sets the telephone number's description to "Additional Telephone"
    And the customer Test does allow solicitations to the telephone number
    And the customer Test adds the new telephone number
    Then no error should occur
    And the customer Test begins editing the last telephone number added
    Then no error should occur
    And the customer Test sets the telephone number's area code to "612"
    And the customer Test sets the telephone number's extension to "200"
    And the customer Test sets the telephone number's description to "Edited Telephone"
    And the customer Test does not allow solicitations to the telephone number
    And the customer Test finishes editing the telephone number
    And the customer Test deletes the last telephone number added
    Then no error should occur
