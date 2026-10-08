@encryption
Feature: External encryption with an IV for each value
  A client supplies EntityEncryptionKeyName to Encrypt and Decrypt
  and stores the IV returned by Encrypt with its ciphertext

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur
    And the user loads the existing base encryption keys
    And the user uses entity encryption key name "CucumberEncryption"

  Scenario: Repeated encryption returns independent IVs and both values remain decryptable
    When the user encrypts "Confidential café 日本語 123" and remembers it as "first"
    Then no error should occur
    When the user encrypts "Confidential café 日本語 123" and remembers it as "second"
    Then no error should occur
    And the encrypted values "first" and "second" have different IVs and ciphertext
    When the user decrypts "first" using its returned IV
    Then no error should occur
    And the decrypted value is "Confidential café 日本語 123"
    When the user decrypts "second" using its returned IV
    Then no error should occur
    And the decrypted value is "Confidential café 日本語 123"

  Scenario: Decrypt accepts the returned IV without Base64 padding
    When the user encrypts "Unpadded IV test" and remembers it as "value"
    Then no error should occur
    When the user decrypts "value" using its returned IV without padding
    Then no error should occur
    And the decrypted value is "Unpadded IV test"

  Scenario: Decrypt requires an IV
    When the user encrypts "Missing IV test" and remembers it as "value"
    Then no error should occur
    When the user decrypts "value" without an IV
    Then the IV validation error "RequiredField" should occur

  Scenario Outline: Decrypt rejects malformed IVs before execution
    When the user encrypts "Invalid IV test" and remembers it as "value"
    Then no error should occur
    When the user decrypts "value" using IV "<iv>"
    Then the IV validation error "<error>" should occur

    Examples:
      | iv                       | error         |
      |                          | RequiredField |
      | parpmcc_number           | InvalidFormat |
      | ______________________== | InvalidFormat |
      | AAAAAAAAAAAAAAAAAAAA     | InvalidFormat |
      | AAAAAAAAAAAAAAAAAAAAAAA= | InvalidFormat |
      | AAAAAAAAAAAAAAAAAAAAAA=  | InvalidFormat |
