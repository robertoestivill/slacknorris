**SlackNorris** is a Slack slash command that displays Chuck Norris quotes from [icndb.com](http://icndb.com) into your Slack conversations.


[![Add to Slack](https://platform.slack-edge.com/img/add_to_slack.png)](https://slack.com/oauth/authorize?scope=commands&client_id=16115056836.16121439510)

![Commands](website/images/image01.jpg)


## Lambdas

Server side logic is implemented using [Serverless Framework](https://serverless.com), which manages [AWS Lambda](https://aws.amazon.com/lambda/) and [API Gateway](https://aws.amazon.com/api-gateway/) and other AWS resources.

Although all the code is hosted in this project, there is a lot of AWS configuration that is not expressed here and will cause this project not to run out of the box.

There are three lambdas in this project and each gets compiled into a different module.


## Website

All static content is served through Github Pages. 

You can find it in the `website` directory.

The original website will be running in [http://robertoestivill.com/slacknorris](http://robertoestivill.com/slacknorris)



## Notes

The website is also mantained in the `master` branch and deployed to `gh-pages`.

Jekyll needs to be installed to build the website 

```sh
$ cd website/
$ jekyll build
```


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

