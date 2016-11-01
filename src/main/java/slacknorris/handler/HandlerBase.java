package slacknorris.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import slacknorris.model.lambda.LambdaRequest;
import slacknorris.model.lambda.LambdaResponse;

import static slacknorris.Redirect.ERROR;

public abstract class HandlerBase implements RequestHandler<LambdaRequest, LambdaResponse> {

  protected LambdaLogger logger;
  protected Gson gson;

  @Override
  public LambdaResponse handleRequest(LambdaRequest request, Context context) {
    logger = context.getLogger();
    logger.log(context.getAwsRequestId() + "\n");
    logger.log(request.httpMethod + " " + request.path + "\n");
    logger.log(request.queryStringParameters + "\n");
    logger.log(request.body + "\n");
    logger.log(request.headers + "\n");

    gson = new Gson();

    try {
      return handle(request);
    } catch (Exception e) {
      logger.log("EXCEPTION " + e.getMessage());
      return redirect(ERROR);
    }
  }

  protected abstract LambdaResponse handle(LambdaRequest request) throws Exception;

  protected LambdaResponse redirect(String url) {
    LambdaResponse res = new LambdaResponse();
    res.headers = new HashMap<>();
    res.headers.put("Location", url);
    res.statusCode = 302;
    return res;
  }

  protected LambdaResponse unauthorized() {
    LambdaResponse res = new LambdaResponse();
    res.statusCode = 401;
    res.body = "Unauthorized";
    return res;
  }

  protected LambdaResponse badRequest() {
    LambdaResponse res = new LambdaResponse();
    res.statusCode = 40;
    res.body = "Bad Request";
    return res;
  }

  protected LambdaResponse ok(String body) {
    LambdaResponse res = new LambdaResponse();
    res.statusCode = 200;
    res.body = body;
    return res;
  }

  protected static Map<String, String> parseForm(String body) throws UnsupportedEncodingException {
    Map<String, String> result = new HashMap<>();
    String[] pairs = body.split("&");
    for (String pair : pairs) {
      String[] each = pair.split("=");
      if (each.length == 1) {
        result.put(each[0], "");
      } else if (each.length == 2) {
        result.put(each[0], URLDecoder.decode(each[1], "UTF-8"));
      }
    }
    return result;
  }
}
