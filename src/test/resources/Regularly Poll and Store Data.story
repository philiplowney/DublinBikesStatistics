Narrative:
In order to present users with up-to-date information
As a provider of network information
I want to have the system poll the JCDeceaux API regularly & show the latest data

Scenario: Poll regularly
Given a test JC Deceaux webservice is listening
Then the webservice is polled periodically

Scenario: Show number of stands that are returned via webservice
Given the webservice is listening with <number_of_stands> random stands
When the user navigates to the "real-time table" screen
Then there will be <number_of_stands> shown