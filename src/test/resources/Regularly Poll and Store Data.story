Narrative:
In order to present users with up-to-date information
As a provider of network information
I want to have the system poll the JCDeceaux API regularly & show the latest data

Scenario: Poll regularly
Given a test JC Deceaux webservice is listening at http://localhost:9000
When the system is deployed
Then the webservice is polled on a regular basis

