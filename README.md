**SlackNorris** is a Slack slash command that displays Chuck Norris quotes from [icndb.com](http://icndb.com) into your Slack conversations.


[![Add to Slack](https://platform.slack-edge.com/img/add_to_slack.png)](https://slack.com/oauth/authorize?scope=commands&client_id=16115056836.16121439510)

![Commands](website/images/image01.jpg)


## Backend

Server side logic is implemented using [Serverless Framework](https://serverless.com), which manages [AWS Lambda](https://aws.amazon.com/lambda/) and [API Gateway](https://aws.amazon.com/api-gateway/) and other AWS resources.

You will need to have AWS credentials and Serverless installed. If you are not familiar with Serverless go ahead a read [the docs](https://serverless.com/framework/docs/)

```sh
$ npm install serverless -g
$ cd slacknorris
$ ./gradlew clean build
$ serverless deploy --stage prod

```

## Configuration

The following environment variables need to be set:

```sh
$ export SLACKNORRIS_CLIENT_ID=[SLACK CLIENT ID]
$ export SLACKNORRIS_CLIENT_SECRET=[SLACK CLIENT SECRET]
$ export SLACKNORRIS_ORIGIN_TOKEN=[SLACK ORIGIN TOKEN]
$ export SLACKNORRIS_EMAIL_SERVER=[SMTP EMAIL SERVER]
$ export SLACKNORRIS_EMAIL_PORT=[SMTP EMAIL PORT]
$ export SLACKNORRIS_EMAIL_USERNAME=[SMTP EMAIL USERNAME]
$ export SLACKNORRIS_EMAIL_PASSWORD=[SMTP EMAIL PASSWORD]
```

The values are used to create a properties file in `src/main/resources/conf/` that will be read on application startup and used for several configurations.

## Website

Jekyll needs to be installed to build the website 

```sh
$ cd website/
$ jekyll build
```

The original website will be running in [https://robertoestivill.com/slacknorris](https://robertoestivill.com/slacknorris)


## License

```
The MIT License (MIT)

Copyright (c) 2016 Roberto Estivill

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

