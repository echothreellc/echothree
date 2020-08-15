Feature: Employee vendor with postal address
  An employee wants to add a vendor with a postal address

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds a vendor, adds a postal address to it, and sets the status of it
    Given the employee Test begins using the application
    And the user begins entering a new vendor
    And the user sets the vendor's name to "Cucumber Postal Address Vendor"
    And the user sets the vendor to use use item purchasing categories
    And the user indicates the vendor does allow solicitations to the email address
    And the user adds the new vendor
    Then no error should occur
    And the user begins entering a new postal address
    And the user sets the postal address's party to the last party added
    And the user sets the postal address's company name to "Cucumber Postal Address Vendor"
    And the user sets the postal address's first name to "Test"
    And the user sets the postal address's last name to "Vendor"
    And the user sets the postal address's line 1 to "256 N Test Street"
    And the user sets the postal address's city to "Des Moines"
    And the user sets the postal address's state to "IA"
    And the user sets the postal address's postal code to "50310"
    And the user sets the postal address's country to "US"
    And the user's postal address is a commercial location
    And the user does not allow solicitations to the postal address
    And the user adds the new postal address
    And the user sets the status of the last vendor added to ACTIVE_TO_INACTIVE
    Then no error should occur
