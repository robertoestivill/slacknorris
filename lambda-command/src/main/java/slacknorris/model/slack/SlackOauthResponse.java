package slacknorris.model.slack;

import com.google.gson.annotations.SerializedName;

public class SlackOauthResponse {

  @SerializedName("ok")
  public Boolean isOk;

  @SerializedName("error")
  public String error;

  @SerializedName("scope")
  public String scope;

  @SerializedName("access_token")
  public String accessToken;

  @SerializedName("team_name")
  public String teamName;

  @SerializedName("team_id")
  public String teamId;
}