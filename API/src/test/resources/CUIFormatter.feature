Feature: CUI Formatter
  Scenario Outline: toSentenceCase
    Given A string of <input>
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
    Then The formatted name will be <output>
    Examples:
    | title | forename | surname | output               |
    |       |          |         | ""                   |
    | ""    | ""       | ""      | ""                   |
    | "mr"  | ""       | ""      | "(Mr)"               |
    | ""    | "mickey" | ""      | "Mickey"             |
    | ""    | ""       | "mouse" | "MOUSE"              |
    | "mr"  | "mickey" | "mouse" | "MOUSE, Mickey (Mr)" |