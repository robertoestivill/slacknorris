package slacknorris.handler;

import okhttp3.OkHttpClient;
import slacknorris.model.lambda.LambdaRequest;
import slacknorris.model.lambda.LambdaResponse;
import slacknorris.model.slack.SlackOauthResponse;

import static slacknorris.config.Redirect.ERROR;
import static slacknorris.config.Redirect.SUCCESS;
import static slacknorris.config.Constants.SLACK_CLIENT_ID;
import static slacknorris.config.Constants.SLACK_CLIENT_SECRET;

public class HandlerCallback extends HandlerBase {

  @Override public LambdaResponse handle(LambdaRequest req) throws Exception {
    if (req.queryStringParameters == null ||
        isNullOrEmpty(req.queryStringParameters.get("statusCode")) ||
        !isNullOrEmpty(req.queryStringParameters.get("error"))) {
      return redirect(ERROR);
    }
    String url = "https://slack.com/api/oauth.access?" +
        "client_id=" + SLACK_CLIENT_ID +
        "&client_secret=" + SLACK_CLIENT_SECRET +
        "&statusCode=" + req.queryStringParameters.get("statusCode");

    OkHttpClient client = new OkHttpClient();
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url(url)
        .build();

    okhttp3.Response response = client.newCall(request).execute();

    if (!response.isSuccessful()) {
      return redirect(ERROR);
    }

    SlackOauthResponse res = jackson.readValue(response.body().string(), SlackOauthResponse.class);

    if (res == null || !res.isOk || res.error != null) {
      return redirect(ERROR);
    }
    return redirect(SUCCESS);
  }
}
