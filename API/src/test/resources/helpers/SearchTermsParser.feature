Feature: Search terms parser
  Scenario Outline: constructor
    Given A search terms of <input>
    When the input is parsed
    Then The parser will be created
    Examples:
    | input           |
    |                 |
    | ""              |
    | "one"           |
    | "one two"       |
    | "one two three" |
    | "two  spaces"   |

  Scenario Outline: NHS number
    Given A search terms of <input>
    When the input is parsed
    Then The parser will only have an NHS number of <output>
    Examples:
      | input          | output       |
      | "1234567890"   | "1234567890" |
      | "123 456 7890" | "1234567890" |

  Scenario Outline: Local identifier
    Given A search terms of <input>
    When the input is parsed
    Then The parser will only have a local identifier of <output>
    Examples:
      | input                  | output                 |
      | "12345678901234567890" | "12345678901234567890" |
      | "12345"                | "12345"                |

  Scenario: Date of birth
    Given A search terms of "23-Apr-1985"
    When the input is parsed
    Then The parser will only have a date of birth of "23/04/1985"

  Scenario Outline: Names
    Given A search terms of <input>
    When the input is parsed
    Then The parser will only have a names of <output>
    Examples:
      | input              | output             |
      | "Smith"            | "Smith"            |
      | "John Smith"       | "John,Smith"       |
      | "John David Smith" | "John,David,Smith" |

