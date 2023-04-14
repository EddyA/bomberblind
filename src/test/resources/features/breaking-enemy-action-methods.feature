Feature: Features of the action methods class for a breaking enemy object

  Scenario: validate breaking enemy dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a breaking enemy at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the breaking enemy
    Then the breaking enemy should die

  Scenario: validate enemy gets another direction when reaching an immutable obstacle
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a breaking enemy at rowIdx 3 and coldIdx 5
    And the breaking enemy is walking to the south
    Given an immutable obstacle at rowIdx 4 and coldIdx 5
    When processing the breaking enemy
    Then the breaking enemy should get another direction

  Scenario: validate enemy breaks the obstacle, adds a flame and gets another direction when reaching a mutable obstacle
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a breaking enemy at rowIdx 3 and coldIdx 5
    And the breaking enemy is walking to the south
    Given a mutable obstacle at rowIdx 4 and coldIdx 5
    When processing the breaking enemy
    Then the breaking enemy should break
    And the breaking sprite is done
    When processing the breaking enemy
    Then the following flames should be added:
#      | rowIdx | colIdx |
      | 4      | 5      |
    Then the breaking enemy should get another direction

  Scenario: validate enemy is marked as removable when dead
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a breaking enemy at rowIdx 3 and coldIdx 5
    And the breaking enemy is dead
    When processing the breaking enemy
    Then the breaking enemy should be marked as removable from the sprite list
