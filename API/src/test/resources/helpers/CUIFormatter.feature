Feature: CUI Formatter
  Scenario Outline: toSentenceCase
    Given A string of <input>
    When the sentence is formatted
    Then The formatted sentence will be <output>
    Examples:
    | input         | output        |
    |               |               |
    | ""            | ""            |
    | "hello world" | "Hello world" |
    | "HELLO WORLD" | "Hello world" |

  Scenario Outline: getFormattedName
    Given A title of <title>
    And A forename of <forename>
    And A surname of <surname>
    When the name is formatted
    Then The formatted name will be <output>
    Examples:
    | title | forename | surname | output               |
    |       |          |         | "Unknown"            |
    | ""    | ""       | ""      | "Unknown"            |
    | "mr"  | ""       | ""      | "(Mr)"               |
    | ""    | "mickey" | ""      | "Mickey"             |
    | ""    | ""       | "mouse" | "MOUSE"              |
    | "mr"  | "mickey" | "mouse" | "MOUSE, Mickey (Mr)" |