package slacknorris.model.slack;

import com.google.gson.annotations.SerializedName;

public class SlackField {

  @SerializedName("title")
  public String title;

  @SerializedName("value")
  public String value;

  @SerializedName("short")
  public Boolean isShort;
}