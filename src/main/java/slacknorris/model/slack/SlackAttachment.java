package slacknorris.model.slack;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SlackAttachment {

  @SerializedName("color")
  public String color;

  @SerializedName("title")
  public String title;

  @SerializedName("title_link")
  public String titleLink;

  @SerializedName("pretext")
  public String pretext;

  @SerializedName("text")
  public String text;

  @SerializedName("author_name")
  public String authorName;

  @SerializedName("author_link")
  public String authorLink;

  @SerializedName("author_icon")
  public String authorIcon;

  @SerializedName("thumb_url")
  public String thumbUrl;

  @SerializedName("fields")
  public List<SlackField> fields;

  @SerializedName("mrkdwn_in")
  public List<String> markdown;
}