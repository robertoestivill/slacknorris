package slacknorris.model.lambda;

import java.util.Map;

public class LambdaRequest {
  public String httpMethod;
  public String path;
  public Map<String, String> queryStringParameters;
  public Map<String, String> headers;
  public String body;
}
