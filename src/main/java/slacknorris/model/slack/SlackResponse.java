package slacknorris.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackResponse {

  @JsonProperty(value = "text")
  public String text;

  @JsonProperty(value = "response_type")
  public String type;

  @JsonProperty(value = "attachments")
  public List<SlackAttachment> attachments;
}
