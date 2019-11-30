Feature: Anonymous user adds customer with login
  An anonymous user wants to add a new customer with login

  Scenario: Anonymous user adds a customer with login and an employee deletes the login
    Given the anonymous user Gerold adds a new customer with the first name "Gerold" and the last name "Jonas" and the email address "gj-test@echothree.com" and does allow solicitations to it and the username "gj" and the first password "my-password" and the second password "my-password" and the recovery question PET_NAME and the answer "Mufasa"
    Then no error should occur
    And the employee Test is not currently logged in
    And the employee Test logs in with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur
    And the employee Test deletes the user login added by the anonymous user Gerold
