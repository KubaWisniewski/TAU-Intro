Scenario: Delete movie from cinema
  Given I have 3 movies in cinema
  When I want to delete one of them. "A1"
  Then Should be 2 movies in cinema list.
