@links
Feature: Employee Links
  Employees create, retrieve, list, and delete Links with cardinality and directionality rules.

  Background:
    Given the employee "Test" begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur
    Given the user has disposable Link endpoints

  Scenario Outline: Links: directed cardinality rejects reuse of a constrained endpoint
    Given the user has Link type "Primary" with cardinality "<cardinality>" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <source> |
      | TargetEntityRef | <target> |
    Then the execution error "LinkCardinalityViolation" should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "1" rows

    Examples:
      | cardinality | source | target |
      | ONE_TO_ONE | A | C |
      | ONE_TO_ONE | C | B |
      | MANY_TO_ONE | A | C |
      | ONE_TO_MANY | C | B |

  Scenario Outline: Links: directed cardinality permits reuse of an unconstrained endpoint
    Given the user has Link type "Primary" with cardinality "<cardinality>" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <source> |
      | TargetEntityRef | <target> |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "2" rows

    Examples:
      | cardinality | source | target |
      | ONE_TO_ONE | C | D |
      | ONE_TO_MANY | A | C |
      | MANY_TO_ONE | C | B |
      | MANY_TO_MANY | A | C |
      | MANY_TO_MANY | C | B |

  Scenario Outline: Links: duplicates return the existing Link without another insertion
    Given the user has Link type "Primary" with cardinality "<cardinality>" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceUuid | A |
      | TargetUuid | B |
    Then the execution error "DuplicateLink" should occur
    And the returned Link connects "A" to "B"
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "1" rows

    Examples:
      | cardinality |
      | ONE_TO_ONE |
      | ONE_TO_MANY |
      | MANY_TO_ONE |
      | MANY_TO_MANY |

  Scenario: Links: directed reverse lookup and deletion cannot change the forward Link
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | B |
      | TargetEntityRef | A |
    Then the execution error "UnknownLink" should occur
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | B |
      | TargetEntityRef | A |
    Then the execution error "UnknownLink" should occur
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    And the returned Link connects "A" to "B"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | B |
      | TargetEntityRef | A |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "2" rows

  Scenario Outline: Links: symmetric reversal is one Link for retrieval, duplication and deletion
    Given the user has Link type "Primary" with cardinality "<cardinality>" and directionality "SYMMETRIC"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceUuid | B |
      | TargetUuid | A |
    Then no error should occur
    And the returned Link connects "A" to "B"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | B |
      | TargetEntityRef | A |
    Then the execution error "DuplicateLink" should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "1" rows
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | B |
      | TargetEntityRef | A |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "0" rows
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "1" rows

    Examples:
      | cardinality |
      | ONE_TO_ONE |
      | MANY_TO_MANY |

  Scenario Outline: Links: symmetric one-to-one constrains both roles of both endpoints
    Given the user has Link type "Primary" with cardinality "ONE_TO_ONE" and directionality "SYMMETRIC"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <source> |
      | TargetEntityRef | <target> |
    Then the execution error "LinkCardinalityViolation" should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "1" rows

    Examples:
      | source | target |
      | A | C |
      | C | A |
      | B | C |
      | C | B |
      | A | A |
      | B | B |

  Scenario: Links: symmetric many-to-many lists follow stored source and target columns
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "SYMMETRIC"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | C |
      | TargetEntityRef | A |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | B |
      | TargetEntityRef | C |
    Then no error should occur
    When the user lists Links with:
      | SourceEntityRef | A |
    Then no error should occur
    And the returned Links contain "1" rows
    And the returned Links are:
      | Type | Source | Target |
      | Primary | A | B |
    When the user lists Links with:
      | TargetUuid | A |
    Then no error should occur
    And the returned Links contain "1" rows
    And the returned Links are:
      | Type | Source | Target |
      | Primary | C | A |

  Scenario Outline: Links: self-links reject duplicates and reserve a one-to-one endpoint
    Given the user has Link type "Primary" with cardinality "ONE_TO_ONE" and directionality "<direction>"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | A |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | A |
    Then the execution error "DuplicateLink" should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then the execution error "LinkCardinalityViolation" should occur
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | A |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur

    Examples:
      | direction |
      | DIRECTED |
      | SYMMETRIC |

  Scenario Outline: Links: EntityRef and UUID endpoint combinations identify the same Link
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | <sourceField> | A |
      | <targetField> | B |
    Then no error should occur
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceUuid | A |
      | TargetUuid | B |
    Then no error should occur
    And the returned Link connects "A" to "B"
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then the execution error "UnknownLink" should occur

    Examples:
      | sourceField | targetField |
      | SourceEntityRef | TargetEntityRef |
      | SourceEntityRef | TargetUuid |
      | SourceUuid | TargetEntityRef |
      | SourceUuid | TargetUuid |

  Scenario: Links: cardinality is scoped to the LinkType and deletion frees the endpoint
    Given the user has Link type "Primary" with cardinality "ONE_TO_ONE" and directionality "DIRECTED"
    Given the user has Link type "Other" with cardinality "ONE_TO_ONE" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Other |
      | SourceEntityRef | A |
      | TargetEntityRef | C |
    Then no error should occur
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | C |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "1" rows

  Scenario: Links: list filters and pagination return current Links and an unpaginated total
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    Given the user has Link type "Other" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | C |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | C |
      | TargetEntityRef | D |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Other |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user lists Links with limit "2" and:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "2" rows
    And the returned Link total count is "3"
    When the user lists Links with:
      | LinkTypeName | Primary |
      | SourceUuid | A |
    Then no error should occur
    And the returned Links contain "2" rows
    When the user lists Links with:
      | LinkTypeName | Primary |
      | TargetEntityRef | B |
    Then no error should occur
    And the returned Links contain "1" rows
    When the user lists Links with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetUuid | B |
    Then no error should occur
    And the returned Links contain "1" rows
    When the user lists Links with:
      | LinkTypeName | Primary |
      | SourceEntityRef | D |
    Then no error should occur
    And the returned Links contain "0" rows
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "2" rows

  Scenario Outline: Links: unknown identifiers and incompatible endpoint types do not create Links
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | <type> |
      | <sourceField> | <source> |
      | <targetField> | <target> |
    Then the execution error "<error>" should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "0" rows

    Examples:
      | type | sourceField | source | targetField | target | error |
      | CucumberMissingLinkType | SourceEntityRef | A | TargetEntityRef | B | UnknownLinkTypeName |
      | Primary | SourceEntityRef | ECHO_THREE.LinkCardinality.9223372036854775807 | TargetEntityRef | B | UnknownEntityRef |
      | Primary | SourceEntityRef | A | TargetEntityRef | ECHO_THREE.LinkCardinality.9223372036854775807 | UnknownEntityRef |
      | Primary | SourceUuid | 00000000-0000-0000-0000-000000000000 | TargetEntityRef | B | UnknownUuid |
      | Primary | SourceEntityRef | A | TargetUuid | 00000000-0000-0000-0000-000000000000 | UnknownUuid |
      | Primary | SourceEntityRef | X | TargetEntityRef | B | InvalidLinkEndpoints |
      | Primary | SourceEntityRef | A | TargetEntityRef | X | InvalidLinkEndpoints |

  Scenario Outline: Links: creates requires exactly one identifier for each endpoint
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the execution error "InvalidParameterCount" should occur

    Examples:
      | sourceRef | sourceUuid | targetRef | targetUuid |
      |  |  | B |  |
      | A | A | B |  |
      | A |  |  |  |
      | A |  | B | B |

  Scenario Outline: Links: retrieves requires exactly one identifier for each endpoint
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the execution error "InvalidParameterCount" should occur

    Examples:
      | sourceRef | sourceUuid | targetRef | targetUuid |
      |  |  | B |  |
      | A | A | B |  |
      | A |  |  |  |
      | A |  | B | B |

  Scenario Outline: Links: deletes requires exactly one identifier for each endpoint
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the execution error "InvalidParameterCount" should occur

    Examples:
      | sourceRef | sourceUuid | targetRef | targetUuid |
      |  |  | B |  |
      | A | A | B |  |
      | A |  |  |  |
      | A |  | B | B |

  Scenario Outline: Links: list rejects ambiguous endpoint identifiers
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user lists Links with:
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the execution error "InvalidParameterCount" should occur

    Examples:
      | sourceRef | sourceUuid | targetRef | targetUuid |
      | A | A |  |  |
      |  |  | B | B |

  Scenario Outline: Links: missing Links cannot be retrieved or deleted
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "<operation>" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then the execution error "UnknownLink" should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario Outline: Links: creates validates identifier formats before execution
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the Link field "<field>" validation error "InvalidFormat" should occur

    Examples:
      | field | sourceRef | sourceUuid | targetRef | targetUuid |
      | SourceEntityRef | bad |  | B |  |
      | SourceUuid |  | bad | B |  |
      | TargetEntityRef | A |  | bad |  |
      | TargetUuid | A |  |  | bad |

  Scenario Outline: Links: retrieves validates identifier formats before execution
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "retrieves" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the Link field "<field>" validation error "InvalidFormat" should occur

    Examples:
      | field | sourceRef | sourceUuid | targetRef | targetUuid |
      | SourceEntityRef | bad |  | B |  |
      | SourceUuid |  | bad | B |  |
      | TargetEntityRef | A |  | bad |  |
      | TargetUuid | A |  |  | bad |

  Scenario Outline: Links: deletes validates identifier formats before execution
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "deletes" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then the Link field "<field>" validation error "InvalidFormat" should occur

    Examples:
      | field | sourceRef | sourceUuid | targetRef | targetUuid |
      | SourceEntityRef | bad |  | B |  |
      | SourceUuid |  | bad | B |  |
      | TargetEntityRef | A |  | bad |  |
      | TargetUuid | A |  |  | bad |

  Scenario Outline: Links: required LinkType name is validated
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "<operation>" a Link with:
      | LinkTypeName |  |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then the Link field "LinkTypeName" validation error "RequiredField" should occur

    Examples:
      | operation |
      | creates |
      | retrieves |
      | deletes |

  Scenario: Links: deleting a LinkType removes all its Links but preserves another type
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    Given the user has Link type "Other" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | C |
      | TargetEntityRef | D |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Other |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user deletes Link test type "Primary"
    Then no error should occur
    When the user lists Links with:
      | SourceEntityRef | A |
    Then no error should occur
    And the returned Links contain "1" rows
    And the returned Links are:
      | Type | Source | Target |
      | Other | A | B |

  Scenario Outline: Links: deleting an endpoint removes incoming, outgoing and self Links
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | C |
      | TargetEntityRef | A |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | A |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | C |
      | TargetEntityRef | D |
    Then no error should occur
    When the user deletes Link test endpoint "<endpoint>"
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | Primary |
    Then no error should occur
    And the returned Links contain "<remaining>" rows

    Examples:
      | endpoint | remaining |
      | A | 1 |
      | B | 3 |

  Scenario Outline: Links: list returns specified filter transfers with matching rows
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    And the user has Link type "Other" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Other |
      | SourceEntityRef | C |
      | TargetEntityRef | D |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | <type> |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then no error should occur
    And the returned Links contain "1" rows
    And the returned Link filters are:
      | LinkType | <expectedType> |
      | SourceEntityInstance | <expectedSource> |
      | TargetEntityInstance | <expectedTarget> |

    Examples:
      | type | sourceRef | sourceUuid | targetRef | targetUuid | expectedType | expectedSource | expectedTarget |
      | Primary |  |  |  |  | Primary | <none> | <none> |
      |  | A |  |  |  | <none> | A | <none> |
      |  |  | A |  |  | <none> | A | <none> |
      |  |  |  | B |  | <none> | <none> | B |
      |  |  |  |  | B | <none> | <none> | B |
      | Primary |  | A |  |  | Primary | A | <none> |
      | Primary |  |  | B |  | Primary | <none> | B |
      |  | A |  |  | B | <none> | A | B |
      | Primary |  | A | B |  | Primary | A | B |
      | Primary | A |  |  | B | Primary | A | B |
      | Primary |  | A |  | B | Primary | A | B |
      | Primary | A |  | B |  | Primary | A | B |

  Scenario Outline: Links: list returns specified filter transfers when no rows match
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    And the user has Link type "Other" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Other |
      | SourceEntityRef | C |
      | TargetEntityRef | D |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | <type> |
      | SourceEntityRef | <sourceRef> |
      | SourceUuid | <sourceUuid> |
      | TargetEntityRef | <targetRef> |
      | TargetUuid | <targetUuid> |
    Then no error should occur
    And the returned Links contain "0" rows
    And the returned Link filters are:
      | LinkType | <expectedType> |
      | SourceEntityInstance | <expectedSource> |
      | TargetEntityInstance | <expectedTarget> |

    Examples:
      | type | sourceRef | sourceUuid | targetRef | targetUuid | expectedType | expectedSource | expectedTarget |
      | Primary |  |  |  |  | Primary | <none> | <none> |
      |  | A |  |  |  | <none> | A | <none> |
      |  |  | A |  |  | <none> | A | <none> |
      |  |  |  | B |  | <none> | <none> | B |
      |  |  |  |  | B | <none> | <none> | B |
      | Primary |  | A |  |  | Primary | A | <none> |
      | Primary |  |  | B |  | Primary | <none> | B |
      |  | A |  |  | B | <none> | A | B |
      | Primary |  | A | B |  | Primary | A | B |
      | Primary | A |  |  | B | Primary | A | B |
      | Primary |  | A |  | B | Primary | A | B |
      | Primary | A |  | B |  | Primary | A | B |

  Scenario: Links: unfiltered lists leave all filter transfers unset
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user lists Links with:
      | LinkTypeName | |
    Then no error should occur
    And the returned Link filters are:
      | LinkType | <none> |
      | SourceEntityInstance | <none> |
      | TargetEntityInstance | <none> |

  Scenario: Links: paginated lists preserve specified filter transfers and totals
    Given the user has Link type "Primary" with cardinality "MANY_TO_MANY" and directionality "DIRECTED"
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | B |
    Then no error should occur
    When the user "creates" a Link with:
      | LinkTypeName | Primary |
      | SourceEntityRef | A |
      | TargetEntityRef | C |
    Then no error should occur
    When the user lists Links with limit "1" and:
      | LinkTypeName | Primary |
      | SourceUuid | A |
    Then no error should occur
    And the returned Links contain "1" rows
    And the returned Link total count is "2"
    And the returned Link filters are:
      | LinkType | Primary |
      | SourceEntityInstance | A |
      | TargetEntityInstance | <none> |
    When the user lists Links with limit "1" and:
      | LinkTypeName | Primary |
      | SourceEntityRef | D |
    Then no error should occur
    And the returned Links contain "0" rows
    And the returned Link total count is "0"
    And the returned Link filters are:
      | LinkType | Primary |
      | SourceEntityInstance | D |
      | TargetEntityInstance | <none> |
