Narrative:
In order to show all of but only current information
As a cycle-network administrator
I want to see all of those stands that were in the latest report from the provider and no other ones

Scenario: Hide stands when they're removed from reports
Given there are 15 stands
When the user navigates to the table-view screen
Then 15 stands will be visible in a table
Given the number of stands is reduced to 13
When the user navigates to the table-view screen
Then 13 stands will be visible in a table

Scenario: Add stands when they're added to reports
Given there are 15 stands
When the user navigates to the table-view screen
Then 15 stands will be visible in a table
Given the number of stands is increased to 20
When the user navigates to the table-view screen
Then 20 stands will be visible in a table