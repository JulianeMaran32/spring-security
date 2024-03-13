# Deep dive of OAuth2 & OpenID Connect

## OAuth2

## Intro to OAuth2 - Problem that OAuth2 solves

- **Twitter App**
- **Twitter user**
- **TweetAnalyzer** website that analyzes user tweets data and generates metrics from it.

**Scenario**: The twitter user want to use an third party website called **TweetAnalyzer**, to get some insights about
his tweets data present inside Twitter App.

* **With out OAuth2**: Twitter user has to share his twitter account credentials to the TweetAnalyzer website. Using
  user credentials, the TweetAnalyzer website will invoke the APIs of Twitter app to fetch the tweet details and post
  that generates a report for the end user.

But it has a bigger disadvantage, the TweetAnalyzer can go fraud and make another operation on your behalf like change
password, change email, make a rouge tweet etc.

* **With OAuth2**: Twitter user doesn't have to share his twitter account credentials to the TweetAnalyzer website.
  Intead he will let Twitter App to give a temporary access token to TweetAnalyzer with limited access like it can only
  read the tweets data.

With this approach, the TweetAnalyzer can only read the tweets data and it can't perform any other operation.

> Stone age approach

