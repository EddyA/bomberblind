Feature: Features of the action methods class for a flame end object

  Scenario: validate flame end is marked as removable when finished
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a flame end at rowIdx 3 and coldIdx 5
    And the flame end is finished
    When processing the flame end
    Then the flame end should be marked as removable from the sprite list