Feature: Employee link cardinality
  An employee wants to add, edit, and delete a link cardinality

  Background:
    Given the employee "Test" begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a link cardinality, edits it, and then deletes it
    Given the employee "Test" begins using the application
    When the user begins entering a new link cardinality
    And the user sets the link cardinality's link cardinality name to "CucumberLinkCardinality"
    And the user sets the link cardinality to "not be" the default
    And the user sets the link cardinality's sort order to "10"
    And the user sets the link cardinality's description to "Cucumber Link Cardinality"
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins specifying a link cardinality to edit
    And the user sets the link cardinality's link cardinality name to the last link cardinality added
    When the user begins editing the link cardinality
    Then no error should occur
    And the user sets the link cardinality's sort order to "20"
    And the user sets the link cardinality's description to "Edited Cucumber Link Cardinality"
    And the user finishes editing the link cardinality
    Then no error should occur
    When the user begins deleting a link cardinality
    And the user sets the link cardinality's link cardinality name to the last link cardinality added
    And the user deletes the link cardinality
    Then no error should occur

  Scenario Outline: LinkCardinality creation requires every mandatory field
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityRequired |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user clears the link cardinality field "<field>"
    And the user adds the new link cardinality
    Then the link cardinality field "<field>" validation error "RequiredField" should occur

    Examples:
      | field |
      | LinkCardinalityName |
      | IsDefault |
      | SortOrder |

  Scenario Outline: LinkCardinality creation rejects malformed values
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityInvalid |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user enters these link cardinality values:
      | <field> | <value> |
    And the user adds the new link cardinality
    Then the link cardinality field "<field>" validation error "InvalidFormat" should occur

    Examples:
      | field | value |
      | IsDefault | perhaps |
      | SortOrder | ten |

  Scenario: LinkCardinality duplicate creation preserves the original values
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityDuplicate |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityDuplicate |
      | IsDefault | false |
      | SortOrder | 99 |
      | Description | Must not replace original |
    And the user adds the new link cardinality
    Then the execution error "DuplicateLinkCardinalityName" should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDuplicate |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | Description | Original description |
      | SortOrder | 10 |
      | IsDefault | false |
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDuplicate |
    Then no error should occur

  Scenario: LinkCardinality duplicate rename leaves the original records unchanged
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityFirst |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalitySecond |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins specifying a link cardinality to edit
    And the user specifies the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalitySecond |
    And the user begins editing the link cardinality
    Then no error should occur
    And the user sets the link cardinality's new link cardinality name to "CucumberLinkCardinalityFirst"
    And the user finishes editing the link cardinality
    Then the execution error "DuplicateLinkCardinalityName" should occur
    When the user abandons editing link cardinality "CucumberLinkCardinalitySecond"
    Then no error should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityFirst |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | LinkCardinalityName | CucumberLinkCardinalityFirst |
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalitySecond |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | LinkCardinalityName | CucumberLinkCardinalitySecond |
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalitySecond |
    Then no error should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityFirst |
    Then no error should occur

  Scenario Outline: LinkCardinality invalid edits leave persisted values unchanged
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityEdit |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins specifying a link cardinality to edit
    And the user specifies the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityEdit |
    And the user begins editing the link cardinality
    Then no error should occur
    And the user enters these link cardinality values:
      | <field> | <value> |
    And the user finishes editing the link cardinality
    Then the link cardinality field "<field>" validation error "<error>" should occur
    When the user abandons editing link cardinality "CucumberLinkCardinalityEdit"
    Then no error should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityEdit |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | Description | Original description |
      | SortOrder | 10 |
      | IsDefault | false |
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityEdit |
    Then no error should occur

    Examples:
      | field | value | error |
      | LinkCardinalityName | | RequiredField |
      | IsDefault | perhaps | InvalidFormat |
      | SortOrder | ten | InvalidFormat |

  Scenario Outline: LinkCardinality unknown names are rejected by each identifying command
    When the user <operation> the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityMissing |
    Then the execution error "UnknownLinkCardinalityName" should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario: LinkCardinality cannot make an unknown record the default
    When the user sets the default link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityMissing |
    Then the execution error "UnknownLinkCardinalityName" should occur

  Scenario: LinkCardinality cannot lock an unknown record for editing
    When the user begins specifying a link cardinality to edit
    And the user specifies the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityMissing |
    And the user begins editing the link cardinality
    Then the execution error "UnknownLinkCardinalityName" should occur

  Scenario Outline: LinkCardinality alternative identifiers cannot be combined
    When the user <operation> the link cardinality with:
      | LinkCardinalityName | MANY_TO_MANY |
      | Uuid | 00000000-0000-0000-0000-000000000001 |
    Then the execution error "InvalidParameterCount" should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario: LinkCardinality set default rejects combined identifiers
    When the user sets the default link cardinality with:
      | LinkCardinalityName | MANY_TO_MANY |
      | Uuid | 00000000-0000-0000-0000-000000000001 |
    Then the execution error "InvalidParameterCount" should occur

  Scenario Outline: LinkCardinality destructive commands require an identifier
    When the user <operation> the link cardinality with:
      | LinkCardinalityName |  |
    Then the execution error "InvalidParameterCount" should occur

    Examples:
      | operation |
      | deletes |

  Scenario: LinkCardinality set default requires an identifier
    When the user sets the default link cardinality with:
      | LinkCardinalityName |  |
    Then the execution error "InvalidParameterCount" should occur

  Scenario: LinkCardinality retrieval supports entity references and deletion removes name lookups
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityReference |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user retrieves the last link cardinality by entity reference
    Then no error should occur
    And the retrieved link cardinality has these values:
      | LinkCardinalityName | CucumberLinkCardinalityReference |
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityReference |
    Then no error should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityReference |
    Then the execution error "UnknownLinkCardinalityName" should occur

  Scenario: LinkCardinality default switching preserves exactly one default
    Given the user remembers the default link cardinality
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultA |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user sets the default link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultA |
    Then no error should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultA |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | IsDefault | true |
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | IsDefault | false |
    When the user sets the default link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
    Then no error should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultA |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | IsDefault | false |
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | IsDefault | true |
    When the user begins specifying a link cardinality to edit
    And the user specifies the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
    And the user begins editing the link cardinality
    Then no error should occur
    And the user sets the link cardinality to "not be" the default
    And the user finishes editing the link cardinality
    Then no error should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | IsDefault | true |
    When the user restores the original default link cardinality
    Then no error should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultB |
    Then no error should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityDefaultA |
    Then no error should occur

  Scenario Outline: LinkCardinality description creation rejects unknown owners and languages
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityOwner |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user "creates" a link cardinality description with:
      | LinkCardinalityName | <owner> |
      | LanguageIsoName | <language> |
      | Description | Description test |
    Then the execution error "<error>" should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityOwner |
    Then no error should occur

    Examples:
      | owner | language | error |
      | CucumberLinkCardinalityMissing | en | UnknownLinkCardinalityName |
      | CucumberLinkCardinalityOwner | cucumber-missing | UnknownLanguageIsoName |

  Scenario Outline: LinkCardinality descriptions require their full key and text
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityRequiredDesc |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user "creates" a link cardinality description with:
      | LinkCardinalityName | <owner> |
      | LanguageIsoName | <language> |
      | Description | <description> |
    Then the link cardinality field "<field>" validation error "RequiredField" should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityRequiredDesc |
    Then no error should occur

    Examples:
      | owner | language | description | field |
      | | fr | French description | LinkCardinalityName |
      | CucumberLinkCardinalityRequiredDesc | | French description | LanguageIsoName |
      | CucumberLinkCardinalityRequiredDesc | fr | | Description |

  Scenario Outline: LinkCardinality missing descriptions cannot be retrieved edited or deleted
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityNoDesc |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user "<operation>" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityNoDesc |
      | LanguageIsoName | fr |
    Then the execution error "UnknownLinkCardinalityDescription" should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityNoDesc |
    Then no error should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario: LinkCardinality missing description cannot be edited
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityNoEditDesc |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user edits the link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityNoEditDesc |
      | LanguageIsoName | fr |
      | Description | Changed |
    Then the execution error "UnknownLinkCardinalityDescription" should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityNoEditDesc |
    Then no error should occur

  Scenario: LinkCardinality description duplicates and invalid edits preserve localized text
    When the user begins entering a new link cardinality
    And the user enters these link cardinality values:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link cardinality
    Then no error should occur
    When the user "creates" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | en |
      | Description | Duplicate text |
    Then the execution error "DuplicateLinkCardinalityDescription" should occur
    When the user "retrieves" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | en |
    Then no error should occur
    And the retrieved link cardinality description is "Original description"
    When the user "creates" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | fr |
      | Description | French description |
    Then no error should occur
    When the user edits the link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | fr |
      | Description |  |
    Then the link cardinality field "Description" validation error "RequiredField" should occur
    When the user "retrieves" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | fr |
    Then no error should occur
    And the retrieved link cardinality description is "French description"
    When the user edits the link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | fr |
      | Description | Edited French description |
    Then no error should occur
    When the user "retrieves" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | fr |
    Then no error should occur
    And the retrieved link cardinality description is "Edited French description"
    When the user "retrieves" a link cardinality description with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
      | LanguageIsoName | en |
    Then no error should occur
    And the retrieved link cardinality description is "Original description"
    When the user lists descriptions for link cardinality "CucumberLinkCardinalityLocalized" with limit "1"
    Then no error should occur
    And the link cardinality description page has 1 entries out of 2
    When the user deletes the link cardinality with:
      | LinkCardinalityName | CucumberLinkCardinalityLocalized |
    Then no error should occur

  Scenario: LinkCardinality cannot be deleted while referenced by a Link Type
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberLinkCardinalityInUse |
      | SourceComponentVendorName | ECHO_THREE |
      | SourceEntityTypeName | Item |
      | TargetComponentVendorName | ECHO_THREE |
      | TargetEntityTypeName | Item |
      | LinkCardinalityName | MANY_TO_MANY |
      | LinkDirectionalityName | DIRECTED |
      | IsTransitive | false |
      | SortOrder | 10 |
    And the user adds the new link type
    Then no error should occur
    When the user deletes the link cardinality with:
      | LinkCardinalityName | MANY_TO_MANY |
    Then the execution error "CannotDeleteLinkCardinalityInUse" should occur
    When the user retrieves the link cardinality with:
      | LinkCardinalityName | MANY_TO_MANY |
    Then no error should occur
    And the retrieved link cardinality has these values:
      | LinkCardinalityName | MANY_TO_MANY |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberLinkCardinalityInUse"
    And the user deletes the link type
    Then no error should occur
