package slacknorris;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CallbackHandler {

  static final String SLACK_CLIENT_ID = "YOUR SLACK CLIENT ID";
  static final String SLACK_CLIENT_SECRET = "YOUR SLACK CLIENT SECRET";

  static final String REDIRECT_HOME = "http://robertoestivill.com/slacknorris";
  static final String REDIRECT_ERROR = REDIRECT_HOME + "/error.html";
  static final String REDIRECT_SUCCESS = REDIRECT_HOME + "/success.html";

  private LambdaLogger logger;

  public static class CallbackRequest {
    public String error;
    public String code;
  }

  public static class CallbackRedirect {
    public String location;

    public CallbackRedirect(String s) {
      this.location = s;
    }
  }

  public static class SlackResponse {
    public boolean ok;
    public String error;
  }

  private boolean isNullOrEmpty(String s ){
    return s == null || s.length() < 1;
  }

  public CallbackRedirect handle(CallbackRequest req, Context context) throws Exception {
    logger = context.getLogger();

    if (req == null || isNullOrEmpty(req.code) || !isNullOrEmpty(req.error) ) {
      return new CallbackRedirect(REDIRECT_ERROR);
    }

    logger.log("Request: [code=" + req.code + ", error=" + req.error + "]");

    String url = "https://slack.com/api/oauth.access?" +
        "client_id=" + SLACK_CLIENT_ID +
        "&client_secret=" + SLACK_CLIENT_SECRET +
        "&code=" + req.code;

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .build();

    Response response = client.newCall(request).execute();

    if (!response.isSuccessful()) {
      return new CallbackRedirect(REDIRECT_ERROR);
    }

    Gson gson = new Gson();
    SlackResponse res = gson.fromJson(response.body().charStream(), SlackResponse.class);

    if (res == null || !res.ok || res.error != null) {
      return new CallbackRedirect(REDIRECT_ERROR);
    }
    return new CallbackRedirect(REDIRECT_SUCCESS);
  }
}