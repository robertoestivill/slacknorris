package slacknorris.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackOauthResponse {

  @JsonProperty(value = "ok")
  public Boolean isOk;

  @JsonProperty(value = "error")
  public String error;

  @JsonProperty(value = "scope")
  public String scope;

  @JsonProperty(value = "access_token")
  public String accessToken;

  @JsonProperty(value = "team_name")
  public String teamName;

  @JsonProperty(value = "team_id")
  public String teamId;
}