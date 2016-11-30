Feature: Features of the action methods class

  # bomber:

  Scenario: validate bomber dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should die

  Scenario: validate bomber dies when crossing an enemy
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given an enemy at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should die

  Scenario: validate bomber does not die when crossing an enemy and invicible
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    And the bomber is invincible
    Given an enemy at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should not die

  Scenario: validate bomber re-inits after dying
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    And the bomber is dead
    When processing the bomber
    Then the bomber is re-init

  # enemy:

  Scenario: validate enemy dies when reaching a burning case
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given an enemy at rowIdx 3 and coldIdx 5
    Given a burning case at rowIdx 3 and coldIdx 5
    When processing the enemy
    Then the enemy should die

  Scenario: validate enemy gets another direction when reaching an obstacle
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given an enemy at rowIdx 3 and coldIdx 5
    And the enemy is walking to the south
    Given an obstacle case at rowIdx 4 and coldIdx 5
    When processing the enemy
    Then the enemy should get another direction