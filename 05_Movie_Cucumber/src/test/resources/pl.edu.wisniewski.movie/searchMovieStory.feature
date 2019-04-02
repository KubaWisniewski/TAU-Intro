Feature: Proof that searching works.
  Narrative: As a spectator
  I want to know that movie is in cinema.

  Scenario Outline: Search for movie record
    Given I have 3 movies in
    Given cinema
    When Spectator ask about "<title>"
    Then Then should get "<answer>"
    Examples:
      | title | answer |
      | A1    | Yes    |
      | A2    | No     |
      | A3    | No     |