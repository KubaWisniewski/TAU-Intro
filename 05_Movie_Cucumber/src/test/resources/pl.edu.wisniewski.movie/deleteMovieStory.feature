Feature: Proof that delete works.
  Narrative: As a director
  I want to delete one movie from cinema

  Scenario: Delete movie from cinema
    Given I have 3 movies in cinema
    When I want to delete one of them. "A1"
    Then Should be 2 movies in cinema list.