Feature: Employee workflow entity type
  An employee wants to add and delete a workflow entity type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a workflow entity type and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new workflow
    And the user sets the workflow's name to "CUCUMBER_WORKFLOW"
    And the user sets the workflow's sort order to "1"
    And the user sets the workflow's description to "Cucumber Workflow"
    And the user adds the new workflow
    Then no error should occur
    When the user begins entering a new workflow entity type
    And the user sets the workflow entity type's workflow name to the last workflow added
    And the user sets the workflow entity type's component vendor to "ECHO_THREE"
    And the user sets the workflow entity type's entity type to "Party"
    And the user adds the new workflow entity type
    Then no error should occur
    When the user begins deleting a workflow entity type
    And the user sets the workflow entity type's workflow name to the last workflow added
    And the user sets the workflow entity type's component vendor to "ECHO_THREE"
    And the user sets the workflow entity type's entity type to "Party"
    And the user deletes the workflow entity type
    Then no error should occur
    When the user begins deleting a workflow
    And the user sets the workflow's name to the last workflow added
    And the user deletes the workflow
    Then no error should occur

  Scenario: Existing employee adds a workflow with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new workflow entity type
    And the user sets the workflow entity type's workflow name to "EMPLOYEE_STATUS"
    And the user sets the workflow entity type's component vendor to "ECHO_THREE"
    And the user sets the workflow entity type's entity type to "Party"
    And the user adds the new workflow entity type
    Then the execution error DuplicateWorkflowEntityTypeName should occur
