Narrative:
In order to present users with up-to-date information
As a provider of network information
I want to have the system poll the JCDeceaux API regularly & show the latest data

Scenario: Poll regularly
Given a test JC Deceaux webservice is listening
Then the webservice is polled periodically
 