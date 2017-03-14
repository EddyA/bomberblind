Feature: Features of the action methods class for a bonus object

  Scenario: validate bonus marked as removable when finished
    Given a MapPoint matrix of 10 rows and 10 cols built with pathway cases
    Given a bonus flame at rowIdx 4 and coldIdx 6
    And the bonus flame is finished
    When processing the bonus flame
    Then the case at rowIdx 4 and coldIdx 6 is no more bonusing
    Then the bonus flame should be marked as removable from the sprite list