Feature: Proof that adding movie works.
  Narrative: As a director
  I want to add new movie to cinema

  Scenario: Add movie to cinema
    Given I have movie
    And I want set title to "A1"
    But Set duration on default "100"
    Then Should be 1 movie in cinema list.
