Feature: Employee item alias type
  An employee wants to add, edit, and delete a item alias type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a item alias type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new item alias type
    And the user sets the item alias type's name to CUCUMBER_ITEM_ALIAS_TYPE
    And the user sets the item alias type's checksum type to "NONE"
    And the user sets the item alias type to not allow multiple values per item
    And the user sets the item alias type's sort order to "1"
    And the user sets the item alias type to be the default
    And the user sets the item alias type's description to "Cucumber Item Alias Type"
    And the user adds the new item alias type
    Then no error should occur
    When the user begins specifying a item alias type to edit
    And the user sets the item alias type's name to the last item alias type added
    When the user begins editing the item alias type
    Then no error should occur
    And the user sets the item alias type's sort order to "2"
    And the user sets the item alias type's description to "Edited Cucumber Item Alias Type"
    And the user finishes editing the item alias type
    Then no error should occur
    When the user begins deleting a item alias type
    And the user sets the item alias type's name to the last item alias type added
    And the user deletes the item alias type
    Then no error should occur

  Scenario: Existing employee adds a item alias type with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new item alias type
    And the user sets the item alias type's name to UPC_A
    And the user sets the item alias type's checksum type to "NONE"
    And the user sets the item alias type to not allow multiple values per item
    And the user sets the item alias type's sort order to "1"
    And the user sets the item alias type to be the default
    And the user adds the new item alias type
    Then the execution error DuplicateItemAliasTypeName should occur
