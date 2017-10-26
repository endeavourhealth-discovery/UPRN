Feature: Security
  Scenario Outline: Allowed organisations
    Given A security context containing <organisations>
    Then The user allowed organisations will be <organisations>
    Examples:
    | organisations         |
    | 1234567890,0987654321 |
