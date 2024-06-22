Feature: Anonymous user adds customer with login
  An anonymous user wants to add a new customer with login

  Scenario: Anonymous user adds a customer with login and an employee deletes the login
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
    And the user adds the new customer
    Then no error should occur
    And the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur
    And the user deletes the user login added by the anonymous user Gerald
