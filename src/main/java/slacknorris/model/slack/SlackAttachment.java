package slacknorris.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackAttachment {

  @JsonProperty(value = "color")
  public String color;

  @JsonProperty(value = "title")
  public String title;

  @JsonProperty(value = "title_link")
  public String titleLink;

  @JsonProperty(value = "pretext")
  public String pretext;

  @JsonProperty(value = "text")
  public String text;

  @JsonProperty(value = "author_name")
  public String authorName;

  @JsonProperty(value = "author_link")
  public String authorLink;

  @JsonProperty(value = "author_icon")
  public String authorIcon;

  @JsonProperty(value = "thumb_url")
  public String thumbUrl;

  @JsonProperty(value = "fields")
  public List<SlackField> fields;

  @JsonProperty(value = "mrkdwn_in")
  public List<String> markdown;
}