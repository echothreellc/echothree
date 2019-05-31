Feature: Customer telephone
  A customer wants to add and delete telephones associated with their account

  Background:
    Given Test is not currently logged in
    When the customer Test logs in with the username "TestC@echothree.com" and password "password"
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone without a description and does not allow solicitations
    Given Test is currently logged in
    And the customer Test adds the telephone in the country "US" with the area code "515" and telephone number "555-1212" and the extension "100" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last telephone added
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone without a description and does allow solicitations
    Given Test is currently logged in
    And the customer Test adds the telephone in the country "US" with the area code "515" and telephone number "555-1212" and the extension "100" and does allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last telephone added
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone with a description and does not allow solicitations
    Given Test is currently logged in
    And the customer Test adds the telephone in the country "US" with the area code "515", telephone number "515-1212" and the extension "100" with the description "Additional Telephone" and does not allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last telephone added
    Then no error should occur

  Scenario: Existing customer adds and then deletes a telephone with a description and does allow solicitations
    Given Test is currently logged in
    And the customer Test adds the telephone in the country "US" with the area code "515", telephone number "515-1212" and the extension "100" with the description "Additional Telephone" and does allow solicitations to it
    Then no error should occur
    And the customer Test deletes the last telephone added
    Then no error should occur
