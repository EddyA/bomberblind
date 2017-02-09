Feature: Features of the action methods class for a flame object

  Scenario: validate flame adds flame end and is marked as removable when finished
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a flame at rowIdx 3 and coldIdx 5
    And the flame is finished
    When processing the flame
    Then a flame end should be added at rowIdx 3 and coldIdx 5
    Then the case at rowIdx 3 and coldIdx 5 is no more burning
    Then the flame should be marked as removable from the sprite list