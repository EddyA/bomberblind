Feature: Features of the action methods class for a walking enemy object

  Scenario: validate walking enemy dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the walking enemy
    Then the walking enemy should die

  Scenario: validate walking enemy gets another direction when reaching an obstacle
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    And the walking enemy is walking to the south
    Given an obstacle case at rowIdx 4 and coldIdx 5
    When processing the walking enemy
    Then the walking enemy should get another direction

  Scenario: validate walking enemy is marked as removable when dead
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    And the walking enemy is dead
    When processing the walking enemy
    Then the walking enemy should be marked as removable from the sprite list