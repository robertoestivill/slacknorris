# Setup

When you create a Slack slash command, a token is provided. This token is sent by Slack everytime a user executes your command. Is a good practice to compare this two to verify that the request is indeed coming from Slack.

To do so, configure your Slack origin token in

`slacknorris.CommandHandler.java` 

in the constant 

```
static final String SLACK_ORIGIN_TOKEN = "YOUR SLACK ORIGIN TOKEN";
```