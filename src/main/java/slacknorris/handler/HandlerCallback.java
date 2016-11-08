package slacknorris.handler;

import okhttp3.OkHttpClient;
import slacknorris.model.lambda.LambdaRequest;
import slacknorris.model.lambda.LambdaResponse;
import slacknorris.model.slack.SlackOauthResponse;

import static slacknorris.config.Redirect.ERROR;
import static slacknorris.config.Redirect.SUCCESS;

public class HandlerCallback extends HandlerBase {

  @Override public LambdaResponse handle(LambdaRequest req) throws Exception {
    if (req.queryStringParameters == null ||
        isNullOrEmpty(req.queryStringParameters.get("code")) ||
        !isNullOrEmpty(req.queryStringParameters.get("error"))) {
      logger.log("Request invalid " + req.queryStringParameters.toString() );
      return redirect(ERROR);
    }
    String url = "https://slack.com/api/oauth.access?" +
        "client_id=" + properties.get("slack.client.id") +
        "&client_secret=" + properties.get("slack.client.secret") +
        "&code=" + req.queryStringParameters.get("code");

    logger.log("Url " + url);

    OkHttpClient client = new OkHttpClient();
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url(url)
        .build();

    okhttp3.Response response = client.newCall(request).execute();

    if (!response.isSuccessful()) {
      logger.log("Response not successful " + response.message());
      return redirect(ERROR);
    }

    SlackOauthResponse res = jackson.readValue(response.body().string(), SlackOauthResponse.class);

    if (res == null || !res.isOk || res.error != null) {
      logger.log("Response invalid " +  res.isOk + " " + res.error);
      return redirect(ERROR);
    }
    logger.log("Response valid");
    return redirect(SUCCESS);
  }
}
