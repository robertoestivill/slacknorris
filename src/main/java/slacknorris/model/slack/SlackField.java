package slacknorris.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackField {

  @JsonProperty(value = "title")
  public String title;

  @JsonProperty(value = "value")
  public String value;

  @JsonProperty(value = "short")
  public Boolean isShort;
}