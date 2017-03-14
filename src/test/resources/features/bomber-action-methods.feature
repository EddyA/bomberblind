Feature: Features of the action methods class for a bomber object

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

  Scenario: validate bomber loses a life after dying
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    And the bomber is dead
    When processing the bomber
    Then the bomber should have 1 bonus heart less

  Scenario: validate bomber re-inits after dying (but not definitively dead)
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    And the bomber is dead
    When processing the bomber
    Then the bomber is re-init

  Scenario: validate bomber get a bonus bomb when crossing a bonus bomb
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a bonus bomb at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should have 1 bonus bomb more

  Scenario: validate bomber get a bonus flame when crossing a bonus flame
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a bonus flame at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should have 1 bonus flame more

  Scenario: validate bomber get a bonus heart when crossing a bonus heart
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a bonus heart at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should have 1 bonus heart more

  Scenario: validate bomber get a bonus roller when crossing a bonus roller
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a bonus roller at rowIdx 3 and coldIdx 5
    When processing the bomber
    Then the bomber should have 1 bonus roller more

  Scenario: validate bomber loses the collected bonus after dying
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    Given a bonus bomb at rowIdx 3 and coldIdx 5
    Given a bonus flame at rowIdx 3 and coldIdx 5
    Given a bonus heart at rowIdx 3 and coldIdx 5
    When processing the bomber
    And the bomber is dead
    When processing the bomber
    Then the bomber should have its bonus rested

  Scenario: validate bomber is marked as removable when definively dead
    Given a MapPoint matrix of 5 rows and 10 cols built with pathway cases
    Given a bomber at rowIdx 3 and coldIdx 5
    And the bomber has 1 lifes
    And the bomber is dead
    When processing the bomber
    Then the bomber should be marked as removable from the sprite list