Feature: Employee link directionality
  An employee wants to add, edit, and delete a link directionality

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a link directionality, edits it, and then deletes it
    Given the employee Test begins using the application
    When the user begins entering a new link directionality
    And the user sets the link directionality's link directionality name to "CucumberLinkDirectionality"
    And the user sets the link directionality to "not be" the default
    And the user sets the link directionality's sort order to "10"
    And the user sets the link directionality's description to "Cucumber Link Directionality"
    And the user adds the new link directionality
    Then no error should occur
    When the user begins specifying a link directionality to edit
    And the user sets the link directionality's link directionality name to the last link directionality added
    When the user begins editing the link directionality
    Then no error should occur
    And the user sets the link directionality's sort order to "20"
    And the user sets the link directionality's description to "Edited Cucumber Link Directionality"
    And the user finishes editing the link directionality
    Then no error should occur
    When the user begins deleting a link directionality
    And the user sets the link directionality's link directionality name to the last link directionality added
    And the user deletes the link directionality
    Then no error should occur

  Scenario Outline: LinkDirectionality creation requires every mandatory field
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityRequired |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user clears the link directionality field <field>
    And the user adds the new link directionality
    Then the link directionality field <field> validation error RequiredField should occur

    Examples:
      | field |
      | LinkDirectionalityName |
      | IsDefault |
      | SortOrder |

  Scenario Outline: LinkDirectionality creation rejects malformed values
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityInvalid |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user enters these link directionality values:
      | <field> | <value> |
    And the user adds the new link directionality
    Then the link directionality field <field> validation error InvalidFormat should occur

    Examples:
      | field | value |
      | IsDefault | perhaps |
      | SortOrder | ten |

  Scenario: LinkDirectionality duplicate creation preserves the original values
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityDuplicate |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityDuplicate |
      | IsDefault | false |
      | SortOrder | 99 |
      | Description | Must not replace original |
    And the user adds the new link directionality
    Then the execution error DuplicateLinkDirectionalityName should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDuplicate |
    Then no error should occur
    And the retrieved link directionality has these values:
      | Description | Original description |
      | SortOrder | 10 |
      | IsDefault | false |
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDuplicate |
    Then no error should occur

  Scenario: LinkDirectionality duplicate rename leaves the original records unchanged
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityFirst |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalitySecond |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user begins specifying a link directionality to edit
    And the user specifies the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalitySecond |
    And the user begins editing the link directionality
    Then no error should occur
    And the user sets the link directionality's new link directionality name to "CucumberLinkDirectionalityFirst"
    And the user finishes editing the link directionality
    Then the execution error DuplicateLinkDirectionalityName should occur
    When the user abandons editing link directionality "CucumberLinkDirectionalitySecond"
    Then no error should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityFirst |
    Then no error should occur
    And the retrieved link directionality has these values:
      | LinkDirectionalityName | CucumberLinkDirectionalityFirst |
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalitySecond |
    Then no error should occur
    And the retrieved link directionality has these values:
      | LinkDirectionalityName | CucumberLinkDirectionalitySecond |
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalitySecond |
    Then no error should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityFirst |
    Then no error should occur

  Scenario Outline: LinkDirectionality invalid edits leave persisted values unchanged
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityEdit |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user begins specifying a link directionality to edit
    And the user specifies the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityEdit |
    And the user begins editing the link directionality
    Then no error should occur
    And the user enters these link directionality values:
      | <field> | <value> |
    And the user finishes editing the link directionality
    Then the link directionality field <field> validation error <error> should occur
    When the user abandons editing link directionality "CucumberLinkDirectionalityEdit"
    Then no error should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityEdit |
    Then no error should occur
    And the retrieved link directionality has these values:
      | Description | Original description |
      | SortOrder | 10 |
      | IsDefault | false |
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityEdit |
    Then no error should occur

    Examples:
      | field | value | error |
      | LinkDirectionalityName | | RequiredField |
      | IsDefault | perhaps | InvalidFormat |
      | SortOrder | ten | InvalidFormat |

  Scenario Outline: LinkDirectionality unknown names are rejected by each identifying command
    When the user <operation> the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityMissing |
    Then the execution error UnknownLinkDirectionalityName should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario: LinkDirectionality cannot make an unknown record the default
    When the user sets the default link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityMissing |
    Then the execution error UnknownLinkDirectionalityName should occur

  Scenario: LinkDirectionality cannot lock an unknown record for editing
    When the user begins specifying a link directionality to edit
    And the user specifies the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityMissing |
    And the user begins editing the link directionality
    Then the execution error UnknownLinkDirectionalityName should occur

  Scenario Outline: LinkDirectionality alternative identifiers cannot be combined
    When the user <operation> the link directionality with:
      | LinkDirectionalityName | DIRECTED |
      | Uuid | 00000000-0000-0000-0000-000000000001 |
    Then the execution error InvalidParameterCount should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario: LinkDirectionality set default rejects combined identifiers
    When the user sets the default link directionality with:
      | LinkDirectionalityName | DIRECTED |
      | Uuid | 00000000-0000-0000-0000-000000000001 |
    Then the execution error InvalidParameterCount should occur

  Scenario Outline: LinkDirectionality destructive commands require an identifier
    When the user <operation> the link directionality with:
      | LinkDirectionalityName |  |
    Then the execution error InvalidParameterCount should occur

    Examples:
      | operation |
      | deletes |

  Scenario: LinkDirectionality set default requires an identifier
    When the user sets the default link directionality with:
      | LinkDirectionalityName |  |
    Then the execution error InvalidParameterCount should occur

  Scenario: LinkDirectionality retrieval supports entity references and deletion removes name lookups
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityReference |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user retrieves the last link directionality by entity reference
    Then no error should occur
    And the retrieved link directionality has these values:
      | LinkDirectionalityName | CucumberLinkDirectionalityReference |
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityReference |
    Then no error should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityReference |
    Then the execution error UnknownLinkDirectionalityName should occur

  Scenario: LinkDirectionality default switching preserves exactly one default
    Given the user remembers the default link directionality
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultA |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user sets the default link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultA |
    Then no error should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultA |
    Then no error should occur
    And the retrieved link directionality has these values:
      | IsDefault | true |
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
    Then no error should occur
    And the retrieved link directionality has these values:
      | IsDefault | false |
    When the user sets the default link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
    Then no error should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultA |
    Then no error should occur
    And the retrieved link directionality has these values:
      | IsDefault | false |
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
    Then no error should occur
    And the retrieved link directionality has these values:
      | IsDefault | true |
    When the user begins specifying a link directionality to edit
    And the user specifies the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
    And the user begins editing the link directionality
    Then no error should occur
    And the user sets the link directionality to "not be" the default
    And the user finishes editing the link directionality
    Then no error should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
    Then no error should occur
    And the retrieved link directionality has these values:
      | IsDefault | true |
    When the user restores the original default link directionality
    Then no error should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultB |
    Then no error should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityDefaultA |
    Then no error should occur

  Scenario Outline: LinkDirectionality description creation rejects unknown owners and languages
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityOwner |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user creates a link directionality description with:
      | LinkDirectionalityName | <owner> |
      | LanguageIsoName | <language> |
      | Description | Description test |
    Then the execution error <error> should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityOwner |
    Then no error should occur

    Examples:
      | owner | language | error |
      | CucumberLinkDirectionalityMissing | en | UnknownLinkDirectionalityName |
      | CucumberLinkDirectionalityOwner | cucumber-missing | UnknownLanguageIsoName |

  Scenario Outline: LinkDirectionality descriptions require their full key and text
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityRequiredDesc |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user creates a link directionality description with:
      | LinkDirectionalityName | <owner> |
      | LanguageIsoName | <language> |
      | Description | <description> |
    Then the link directionality field <field> validation error RequiredField should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityRequiredDesc |
    Then no error should occur

    Examples:
      | owner | language | description | field |
      | | fr | French description | LinkDirectionalityName |
      | CucumberLinkDirectionalityRequiredDesc | | French description | LanguageIsoName |
      | CucumberLinkDirectionalityRequiredDesc | fr | | Description |

  Scenario Outline: LinkDirectionality missing descriptions cannot be retrieved edited or deleted
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityNoDesc |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user <operation> a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityNoDesc |
      | LanguageIsoName | fr |
    Then the execution error UnknownLinkDirectionalityDescription should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityNoDesc |
    Then no error should occur

    Examples:
      | operation |
      | retrieves |
      | deletes |

  Scenario: LinkDirectionality missing description cannot be edited
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityNoEditDesc |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user edits the link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityNoEditDesc |
      | LanguageIsoName | fr |
      | Description | Changed |
    Then the execution error UnknownLinkDirectionalityDescription should occur
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityNoEditDesc |
    Then no error should occur

  Scenario: LinkDirectionality description duplicates and invalid edits preserve localized text
    When the user begins entering a new link directionality
    And the user enters these link directionality values:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | IsDefault | false |
      | SortOrder | 10 |
      | Description | Original description |
    And the user adds the new link directionality
    Then no error should occur
    When the user creates a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | en |
      | Description | Duplicate text |
    Then the execution error DuplicateLinkDirectionalityDescription should occur
    When the user retrieves a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | en |
    Then no error should occur
    And the retrieved link directionality description is "Original description"
    When the user creates a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | fr |
      | Description | French description |
    Then no error should occur
    When the user edits the link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | fr |
      | Description |  |
    Then the link directionality field Description validation error RequiredField should occur
    When the user retrieves a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | fr |
    Then no error should occur
    And the retrieved link directionality description is "French description"
    When the user edits the link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | fr |
      | Description | Edited French description |
    Then no error should occur
    When the user retrieves a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | fr |
    Then no error should occur
    And the retrieved link directionality description is "Edited French description"
    When the user retrieves a link directionality description with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
      | LanguageIsoName | en |
    Then no error should occur
    And the retrieved link directionality description is "Original description"
    When the user lists descriptions for link directionality "CucumberLinkDirectionalityLocalized" with limit "1"
    Then no error should occur
    And the link directionality description page has 1 entry out of 2
    When the user deletes the link directionality with:
      | LinkDirectionalityName | CucumberLinkDirectionalityLocalized |
    Then no error should occur

  Scenario: LinkDirectionality cannot be deleted while referenced by a Link Type
    When the user begins entering a new link type
    And the user enters these link type values:
      | LinkTypeName | CucumberLinkDirectionalityInUse |
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
    When the user deletes the link directionality with:
      | LinkDirectionalityName | DIRECTED |
    Then the execution error CannotDeleteLinkDirectionalityInUse should occur
    When the user retrieves the link directionality with:
      | LinkDirectionalityName | DIRECTED |
    Then no error should occur
    And the retrieved link directionality has these values:
      | LinkDirectionalityName | DIRECTED |
    When the user begins deleting a link type
    And the user sets the link type's link type name to "CucumberLinkDirectionalityInUse"
    And the user deletes the link type
    Then no error should occur
