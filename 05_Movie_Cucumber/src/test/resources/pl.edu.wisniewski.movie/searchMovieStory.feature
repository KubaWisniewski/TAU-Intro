Feature: Proof that searching works.
  Narrative: As a spectator
  I want to know that movie is in cinema.

  Scenario: Search for movie record
    Given I have 3 movies in
    Given cinema
    When Spectator ask about "A1"
    Then Then should get "Yes"
