Feature: Features of the action methods class for a bomb object

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
#| rowIdx | colIdx |
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
