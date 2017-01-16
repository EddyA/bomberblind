Feature: Features of the action methods class

  # bomber:

  Scenario: validate bomber dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should die

  Scenario: validate bomber dies when crossing a walking enemy
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a walking enemy at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should die

  Scenario: validate bomber dies when crossing a breaking enemy
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a breaking enemy at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should die

  Scenario: validate bomber does not die when crossing an enemy and invincible
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    And the bomber is invincible
    Given a walking enemy at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should not die

  Scenario: validate bomber re-inits after dying
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    And the bomber is dead
    When processing the bomber
    Then the bomber is re-init

  # walking enemy:

  Scenario: validate walking enemy dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the enemy
    Then the walking enemy should die

  Scenario: validate walking enemy gets another direction when reaching an obstacle
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    And the walking enemy is walking to the south
    Given an obstacle case at rowIdx 4 and coldIdx 5
    When processing the enemy
    Then the walking enemy should get another direction

  Scenario: validate walking enemy is marked as removable when dead
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    And the walking enemy is dead
    When processing the enemy
    Then the walking enemy should be marked as removable from the sprite list

  # breaking enemy:

  Scenario: validate enemy dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the enemy
    Then the walking enemy should die

  Scenario: validate enemy gets another direction when reaching an obstacle
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    And the walking enemy is walking to the south
    Given an obstacle case at rowIdx 4 and coldIdx 5
    When processing the enemy
    Then the walking enemy should get another direction

  Scenario: validate enemy is marked as removable when dead
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a walking enemy at rowIdx 3 and coldIdx 5
    And the walking enemy is dead
    When processing the enemy
    Then the walking enemy should be marked as removable from the sprite list

  # bomb:

  Scenario: validate bomb ends when reaching a burning case
    Given a MapPoint matrix of 10 rows and 10 cols built with pathway cases
    Given a bomb at rowIdx 4 and coldIdx 4 and a flame size of 3
    Given a burning case at rowIdx 4 and coldIdx 4
    When processing the bomb
    Then the bomb should be finished

  Scenario: validate bomb adds flames and is marked as removable when finished
    Given a MapPoint matrix of 10 rows and 10 cols built with pathway cases
    Given a bomb at rowIdx 4 and coldIdx 4 and a flame size of 3
    And the bomb is finished
    When processing the bomb
    Then the following flames should be added:
      | rowIdx | colIdx |
      | 1      | 4      |
      | 2      | 4      |
      | 3      | 4      |
      | 4      | 4      |
      | 5      | 4      |
      | 6      | 4      |
      | 7      | 4      |
      | 4      | 1      |
      | 4      | 2      |
      | 4      | 3      |
      | 4      | 5      |
      | 4      | 6      |
      | 4      | 7      |
    Then the case at rowIdx 4 and coldIdx 4 is no more bombing
    Then the bomb should be marked as removable from the sprite list

  # flame:

  Scenario: validate flame adds flame end and is marked as removable when finished
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a flame at rowIdx 3 and coldIdx 5
    And the flame is finished
    When processing the flame
    Then a flame end should be added at rowIdx 3 and coldIdx 5
    Then the case at rowIdx 3 and coldIdx 5 is no more burning
    Then the flame should be marked as removable from the sprite list

  # flame end:

  Scenario: validate flame end is marked as removable when finished
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a flame end at rowIdx 3 and coldIdx 5
    And the flame end is finished
    When processing the flame end
    Then the flame end should be marked as removable from the sprite list