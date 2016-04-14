**SlackNorris** is a Slack slash command that displays Chuck Norris quotes from [icndb.com](http://icndb.com) into your Slack conversations.


## Website

All static content is served through Github Pages. 

You can find it in the `website` directory.

The original website will be running in [http://robertoestivill.com/slacknorris](http://robertoestivill.com/slacknorris)


## Lambdas

Server side logic is implemented using [AWS Lambda](https://aws.amazon.com/lambda/) and [API Gateway](https://aws.amazon.com/api-gateway/).

Although all the code is hosted in this project, there is a lot of AWS configuration that is not expressed here and will cause this project not to run out of the box.

There are three lambdas in this project and each gets compiled into a different module.


### lambda-contact

Receives form data from the website, sends an email with the information and redirects to the static website.


### lambda-callback

Receives a call from Slack OAuth process, performs a confirmation request to Slack to finish the OAuth signup and redirects to the success/error static website.


### lambda-command

Receives a call from Slack to execute the slash command. Performs parameters checks and cleanups, executes a network call to icndb.com and returns the quote in the Slack format.



## Notes

The website is also mantain in the `master` branch and deployed to `gh-pages` by running

`git subtree push --prefix website origin gh-pages`


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

