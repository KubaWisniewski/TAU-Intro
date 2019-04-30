Scenario: Add movie to cinema
  Given I have movie with title "A1"
  When I want set duration on "100"
  Then Should be 1 movie in cinema list.
