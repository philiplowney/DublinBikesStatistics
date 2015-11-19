Narrative:
In order to quickly gain a detailed view of the network's current state
As a cycle-network administrator
I want to see a table of all stands and their current states

Scenario: See all stands in network
Given there are 15 stands
And the bike stands have random capacity and occupancy
When the user navigates to the table-view screen
Then 15 stands will be visible in a table

Scenario: View capacity and occupancy
Given all the bike stands currently have a capacity of 20 and an occupancy of 10
When the user navigates to the table-view screen
Then all stands will have a capacity of 20
And all stands will have an occupancy of 10

Scenario: Order by columns
Given the bike stands have random capacity and occupancy
When the user navigates to the table-view screen
And the user orders the table by <column>
Then the table is ordered by <column>

Examples:     
|column|
|Bikes|
|Stand|
|Spaces|

Scenario: Highlight empty stands
Given all the bike stands currently have a capacity of 20 and an occupancy of 0
When the user navigates to the table-view screen
Then all stands are highlighted as empty

Scenario: Highlight full stands
Given all the bike stands currently have a capacity of 10 and an occupancy of 10
When the user navigates to the table-view screen
Then all stands are highlighted as full

