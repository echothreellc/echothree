Feature: Employee link type
  An employee wants to add, edit, and delete a link type

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a link type, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new link type
    And the user sets the link type's link type name to "CucumberLinkType"
    And the user sets the link type's source component vendor name to "ECHO_THREE"
    And the user sets the link type's source entity type name to "Item"
    And the user sets the link type's target component vendor name to "ECHO_THREE"
    And the user sets the link type's target entity type name to "Item"
    And the user sets the link type's link cardinality name to "MANY_TO_MANY"
    And the user sets the link type's link directionality name to "DIRECTED"
    And the user sets the link type's is transitive to "false"
    And the user sets the link type's guidance MIME type name to "text/plain"
    And the user sets the link type's guidance to "Use this relationship for the Cucumber test."
    And the user sets the link type's sort order to "10"
    And the user sets the link type's description to "Cucumber Link Type"
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to the last link type added
    When the user begins editing the link type
    Then no error should occur
    And the user sets the link type's sort order to "20"
    And the user sets the link type's description to "Edited Cucumber Link Type"
    And the user finishes editing the link type
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to the last link type added
    And the user deletes the link type
    Then no error should occur

  Scenario Outline: Link Type creation requires every mandatory field
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberRequiredLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user clears the link type field <field>
    And the user adds the new link type
    Then the link type field <field> validation error RequiredField should occur

    Examples:
      | field |
      | LinkTypeName |
      | SourceComponentVendorName |
      | SourceEntityTypeName |
      | TargetComponentVendorName |
      | TargetEntityTypeName |
      | LinkCardinalityName |
      | LinkDirectionalityName |
      | IsTransitive |
      | SortOrder |

  Scenario Outline: Link Type creation rejects unknown references
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberUnknownLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user enters these link type values:
      | <field> | <value> |
    And the user adds the new link type
    Then the execution error <error> should occur

    Examples:
      | field | value | error |
      | SourceComponentVendorName | CucumberMissingVendor | UnknownComponentVendorName |
      | TargetComponentVendorName | CucumberMissingVendor | UnknownComponentVendorName |
      | SourceEntityTypeName | CucumberMissingEntity | UnknownEntityTypeName |
      | TargetEntityTypeName | CucumberMissingEntity | UnknownEntityTypeName |
      | LinkCardinalityName | CucumberMissingCardinality | UnknownLinkCardinalityName |
      | LinkDirectionalityName | CucumberMissingDirection | UnknownLinkDirectionalityName |
      | ParentLinkTypeName | CucumberMissingLinkType | UnknownLinkTypeName |
      | InverseLinkTypeName | CucumberMissingLinkType | UnknownLinkTypeName |

  Scenario Outline: Link Type rejects incompatible symmetric and transitive endpoints
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberInvalidEndpoints |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | <source> |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | <target> |
      | LinkCardinalityName | <cardinality> |
      | LinkDirectionalityName | <direction> |
      | IsTransitive | <transitive> |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then the execution error InvalidLinkTypeEndpoints should occur

    Examples:
      | source | target | cardinality | direction | transitive |
      | Item | Party | MANY_TO_MANY | SYMMETRIC | false |
      | Item | Party | MANY_TO_MANY | DIRECTED | true |
      | Item | Item | MANY_TO_ONE | SYMMETRIC | false |
      | Item | Item | ONE_TO_MANY | SYMMETRIC | false |

  Scenario Outline: Link Type guidance must have a compatible MIME type and description
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberInvalidGuidance |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user clears the link type field Description
    And the user enters these link type values:
      | Description | <description> |
      | GuidanceMimeTypeName | <mimeType> |
      | Guidance | <guidance> |
    And the user adds the new link type
    Then the execution error <error> should occur

    Examples:
      | description | mimeType | guidance | error |
      | Guidance test | | Explain this link | MissingRequiredGuidanceMimeTypeName |
      | Guidance test | text/plain | | MissingRequiredGuidance |
      | | text/plain | Explain this link | MissingRequiredLinkTypeDescription |
      | Guidance test | application/x-cucumber-missing | Explain this link | UnknownGuidanceMimeTypeName |
      | Guidance test | image/png | Explain this link | UnknownGuidanceMimeTypeUsage |

  Scenario: Link Type duplicate creation preserves the original definition
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberDuplicateLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberDuplicateLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Must not replace the original |
    And the user adds the new link type
    Then the execution error DuplicateLinkTypeName should occur
    When the user retrieves link type "CucumberDuplicateLinkType"
    Then no error should occur
    And the retrieved link type has these values:
      | Description | Original link type |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberDuplicateLinkType"
    And the user deletes the link type
    Then no error should occur

  Scenario Outline: Link Type parent compatibility rejects broader or incompatible children
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberParentLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | <parentCardinality> |
      | LinkDirectionalityName | <parentDirection> |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberChildLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | <target> |
      | LinkCardinalityName | <childCardinality> |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | ParentLinkTypeName | CucumberParentLinkType |
    And the user adds the new link type
    Then the execution error InvalidLinkTypeEndpoints should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberParentLinkType"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | parentCardinality | parentDirection | target | childCardinality |
      | MANY_TO_ONE | DIRECTED | Item | MANY_TO_MANY |
      | MANY_TO_MANY | DIRECTED | Party | MANY_TO_MANY |
      | MANY_TO_MANY | SYMMETRIC | Item | MANY_TO_MANY |

  Scenario Outline: Link Type inverse compatibility checks both ends and relationship metadata
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberInverseLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | <inverseSource> |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | <inverseTarget> |
      | LinkCardinalityName | <inverseCardinality> |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | <inverseTransitive> |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberProposedLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | <target> |
      | LinkCardinalityName | MANY_TO_ONE |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | InverseLinkTypeName | CucumberInverseLinkType |
    And the user adds the new link type
    Then the execution error InvalidLinkTypeInverse should occur
    When the user retrieves link type "CucumberInverseLinkType"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | <none> |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberInverseLinkType"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | target | inverseSource | inverseTarget | inverseCardinality | inverseTransitive |
      | Party | Item | Party | ONE_TO_MANY | false |
      | Party | Party | Item | MANY_TO_ONE | false |
      | Item | Item | Item | ONE_TO_MANY | true |

  Scenario Outline: Link Type rejects self references during an edit
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberSelfLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to "CucumberSelfLinkType"
    And the user begins editing the link type
    Then no error should occur
    And the user enters these link type values:
      | <field> | CucumberSelfLinkType |
    And the user finishes editing the link type
    Then the execution error <error> should occur
    When the user abandons editing link type "CucumberSelfLinkType"
    Then no error should occur
    When the user retrieves link type "CucumberSelfLinkType"
    Then no error should occur
    And the retrieved link type has these values:
      | ParentLinkTypeName | <none> |
      | InverseLinkTypeName | <none> |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberSelfLinkType"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | field | error |
      | ParentLinkTypeName | LinkTypeParentCycle |
      | InverseLinkTypeName | InvalidLinkTypeInverse |

  Scenario: Link Type prevents indirect parent cycles and deleting a parent in use
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberCycleParent |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberCycleChild |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | ParentLinkTypeName | CucumberCycleParent |
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to "CucumberCycleParent"
    And the user begins editing the link type
    Then no error should occur
    And the user sets the link type's parent link type name to "CucumberCycleChild"
    And the user finishes editing the link type
    Then the execution error LinkTypeParentCycle should occur
    When the user abandons editing link type "CucumberCycleParent"
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberCycleParent"
    And the user deletes the link type
    Then the execution error LinkTypeInUse should occur
    When the user retrieves link type "CucumberCycleChild"
    Then no error should occur
    And the retrieved link type has these values:
      | ParentLinkTypeName | CucumberCycleParent |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberCycleChild"
    And the user deletes the link type
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberCycleParent"
    And the user deletes the link type
    Then no error should occur

  Scenario Outline: Link Type parent edits cannot invalidate existing children
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberEditParent |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberEditChild |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | ParentLinkTypeName | CucumberEditParent |
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to "CucumberEditParent"
    And the user begins editing the link type
    Then no error should occur
    And the user enters these link type values:
      | <field> | <value> |
    And the user finishes editing the link type
    Then the execution error InvalidLinkTypeEndpoints should occur
    When the user abandons editing link type "CucumberEditParent"
    Then no error should occur
    When the user retrieves link type "CucumberEditParent"
    Then no error should occur
    And the retrieved link type has these values:
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | TargetEntityTypeName | Item |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberEditChild"
    And the user deletes the link type
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberEditParent"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | field | value |
      | LinkCardinalityName | ONE_TO_ONE |
      | LinkDirectionalityName | SYMMETRIC |
      | TargetEntityTypeName | Party |

  Scenario: Link Type reciprocal inverses cannot be stolen and are cleared on deletion
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberInverseFirst |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberInverseSecond |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | InverseLinkTypeName | CucumberInverseFirst |
    And the user adds the new link type
    Then no error should occur
    When the user retrieves link type "CucumberInverseFirst"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | CucumberInverseSecond |
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberInverseThird |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | InverseLinkTypeName | CucumberInverseFirst |
    And the user adds the new link type
    Then the execution error InvalidLinkTypeInverse should occur
    When the user retrieves link type "CucumberInverseFirst"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | CucumberInverseSecond |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberInverseSecond"
    And the user deletes the link type
    Then no error should occur
    When the user retrieves link type "CucumberInverseFirst"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | <none> |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberInverseFirst"
    And the user deletes the link type
    Then no error should occur

  Scenario: Link Type duplicate rename leaves both original types unchanged
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberRenameFirst |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberRenameSecond |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to "CucumberRenameSecond"
    And the user begins editing the link type
    Then no error should occur
    And the user sets the link type's new link type name to "CucumberRenameFirst"
    And the user finishes editing the link type
    Then the execution error DuplicateLinkTypeName should occur
    When the user abandons editing link type "CucumberRenameSecond"
    Then no error should occur
    When the user retrieves link type "CucumberRenameSecond"
    Then no error should occur
    And the retrieved link type has these values:
      | LinkTypeName | CucumberRenameSecond |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberRenameSecond"
    And the user deletes the link type
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberRenameFirst"
    And the user deletes the link type
    Then no error should occur

  Scenario: Link Type cannot retrieve or delete an unknown type
    When the user retrieves link type "CucumberMissingLinkType"
    Then the execution error UnknownLinkTypeName should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberMissingLinkType"
    And the user deletes the link type
    Then the execution error UnknownLinkTypeName should occur

  Scenario Outline: Link Type translation creation rejects unknown owners and languages
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberTranslationOwner |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user creates a link type translation with:
      | LinkTypeName | <linkType> |
      | LanguageIsoName | <language> |
      | Description | Translation test |
    Then the execution error <error> should occur

    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberTranslationOwner"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | linkType | language | error |
      | CucumberMissingLinkType | en | UnknownLinkTypeName |
      | CucumberTranslationOwner | cucumber-missing | UnknownLanguageIsoName |

  Scenario: Link Type duplicate translation and unknown translation deletion preserve existing text
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberTranslatedLinkType |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user creates a link type translation with:
      | LinkTypeName | CucumberTranslatedLinkType |
      | LanguageIsoName | en |
      | Description | Must not replace the original |
    Then the execution error DuplicateLinkTypeTranslation should occur
    When the user retrieves link type "CucumberTranslatedLinkType"
    Then no error should occur
    And the retrieved link type has these values:
      | Description | Original link type |
    When the user deletes the "en" translation of link type "CucumberTranslatedLinkType"
    Then no error should occur
    When the user deletes the "en" translation of link type "CucumberTranslatedLinkType"
    Then the execution error UnknownLinkTypeTranslation should occur
    When the user creates a link type translation with:
      | LinkTypeName | CucumberTranslatedLinkType |
      | LanguageIsoName | en |
      | Description | Replacement translation |
    Then no error should occur
    When the user retrieves link type "CucumberTranslatedLinkType"
    Then no error should occur
    And the retrieved link type has these values:
      | Description | Replacement translation |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberTranslatedLinkType"
    And the user deletes the link type
    Then no error should occur

  Scenario Outline: Link Type failed edits preserve the stored definition
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberFailedEdit |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to "CucumberFailedEdit"
    And the user begins editing the link type
    Then no error should occur
    And the user enters these link type values:
      | <field> | <value> |
    And the user finishes editing the link type
    Then the execution error <error> should occur
    When the user abandons editing link type "CucumberFailedEdit"
    Then no error should occur
    When the user retrieves link type "CucumberFailedEdit"
    Then no error should occur
    And the retrieved link type has these values:
      | SourceEntityTypeName | Item |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | ParentLinkTypeName | <none> |
      | InverseLinkTypeName | <none> |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberFailedEdit"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | field | value | error |
      | SourceEntityTypeName | CucumberMissingEntity | UnknownEntityTypeName |
      | TargetEntityTypeName | CucumberMissingEntity | UnknownEntityTypeName |
      | LinkCardinalityName | CucumberMissingCardinality | UnknownLinkCardinalityName |
      | LinkDirectionalityName | CucumberMissingDirection | UnknownLinkDirectionalityName |
      | ParentLinkTypeName | CucumberMissingLinkType | UnknownLinkTypeName |
      | InverseLinkTypeName | CucumberMissingLinkType | UnknownLinkTypeName |

  Scenario Outline: Link Type translation guidance is validated before writing
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberGuidanceTranslation |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user deletes the "en" translation of link type "CucumberGuidanceTranslation"
    Then no error should occur
    When the user creates a link type translation with:
      | LinkTypeName | CucumberGuidanceTranslation |
      | LanguageIsoName | en |
      | Description | Translation guidance |
      | GuidanceMimeTypeName | <mimeType> |
      | Guidance | <guidance> |
    Then the execution error <error> should occur
    When the user deletes the "en" translation of link type "CucumberGuidanceTranslation"
    Then the execution error UnknownLinkTypeTranslation should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberGuidanceTranslation"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | mimeType | guidance | error |
      | | Explain this link | MissingRequiredGuidanceMimeTypeName |
      | text/plain | | MissingRequiredGuidance |
      | application/x-cucumber-missing | Explain this link | UnknownGuidanceMimeTypeName |
      | image/png | Explain this link | UnknownGuidanceMimeTypeUsage |

  Scenario Outline: Link Type accepts supported symmetric and transitive definitions
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberValidDefinition |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | <cardinality> |
      | LinkDirectionalityName | <direction> |
      | IsTransitive | <transitive> |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user retrieves link type "CucumberValidDefinition"
    Then no error should occur
    And the retrieved link type has these values:
      | LinkCardinalityName | <cardinality> |
      | LinkDirectionalityName | <direction> |
      | IsTransitive | <transitive> |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberValidDefinition"
    And the user deletes the link type
    Then no error should occur

    Examples:
      | cardinality | direction | transitive |
      | ONE_TO_ONE | SYMMETRIC | false |
      | MANY_TO_MANY | SYMMETRIC | false |
      | MANY_TO_MANY | DIRECTED | true |

  Scenario: Link Type inverse reassignment clears the old reciprocal reference
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberReassignFirst |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberReassignSecond |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
      | InverseLinkTypeName | CucumberReassignFirst |
    And the user adds the new link type
    Then no error should occur
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberReassignThird |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
      | Description | Original link type |
    And the user adds the new link type
    Then no error should occur
    When the user begins specifying a link type to edit
    And the user sets the link type's link type name to "CucumberReassignSecond"
    And the user begins editing the link type
    Then no error should occur
    And the user sets the link type's inverse link type name to "CucumberReassignThird"
    And the user finishes editing the link type
    Then no error should occur
    When the user retrieves link type "CucumberReassignFirst"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | <none> |
    When the user retrieves link type "CucumberReassignSecond"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | CucumberReassignThird |
    When the user retrieves link type "CucumberReassignThird"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | CucumberReassignSecond |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberReassignSecond"
    And the user deletes the link type
    Then no error should occur
    When the user retrieves link type "CucumberReassignThird"
    Then no error should occur
    And the retrieved link type has these values:
      | InverseLinkTypeName | <none> |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberReassignThird"
    And the user deletes the link type
    Then no error should occur
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberReassignFirst"
    And the user deletes the link type
    Then no error should occur
