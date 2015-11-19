Narrative:
In order to quickly gain an overall view of the network's current state
As a cycle-network administrator
I want to see a dashboard of the main statistics describing the network's current state

Scenario: View Aggregrate Bikes-To-Spaces Pie-Chart
Given there are <number_of_stands> stands 
And all the bike stands currently have a capacity of <capacity> and an occupancy of <occupancy>
When the user navigates to the dashboard screen
Then the bikes-to-spaces piechart will show <total_bikes> total available bikes
And the bikes-to-spaces piechart will show <total_free_stands> total available stands

Examples:
|number_of_stands|capacity|occupancy|total_bikes|total_free_stands|
|10|20|10|100|100|
|10|10|3|30|70|
|20|20|10|200|200|