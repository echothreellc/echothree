Feature: Employee customer alias
  An employee wants to add, edit, and delete a customer alias

  Scenario: Anonymous user adds a customer with login and an employee adds, edits, and deletes a party alias
    Given the anonymous user Gerald begins using the application
    Given the user begins adding a new customer
    And the user sets the new customer's first name to "Gerald"
    And the user sets the new customer's last name to "Jonas"
    And the user sets the new customer's email address to "gj-test@echothree.com"
    And the user does not allow solicitations to the new customer
    And the user sets the new customer's username to "gj"
    And the user sets the new customer's first password to "my-password"
    And the user sets the new customer's second password to "my-password"
    And the user sets the new customer's recovery question to PET_NAME
    And the user sets the new customer's answer to "Mufasa"
    When the user adds the new customer
    Then no error should occur
    Given the employee Test begins using the application
    And the user is not currently logged in
    Given the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    When the employee logs in
    Then no error should occur
    Given the user begins entering a new party alias
    And the user sets the party alias's party added by the anonymous user Gerald
    And the user sets the party alias type's party alias type to TEST
    And the user sets the party alias's alias to "MY_TEST_ALIAS"
    When the user adds the new party alias
    Then no error should occur
    Given the user begins specifying a party alias to edit
    And the user sets the party alias's party added by the anonymous user Gerald
    And the user sets the party alias type's party alias type to TEST
    When the user begins editing the party alias
    Then no error should occur
    And the user sets the party alias's alias to "MY_EDITED_TEST_ALIAS"
    And the user finishes editing the party alias
    Then no error should occur
    Given the user begins deleting a party alias
    And the user sets the party alias's party added by the anonymous user Gerald
    And the user sets the party alias type's party alias type to TEST
    And the user deletes the party alias
    Then no error should occur
    And the user deletes the user login added by the anonymous user Gerald
