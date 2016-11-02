package slacknorris.model.icndb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IcndbQuote {

  @JsonProperty(value = "id")
  public int id;

  @JsonProperty(value = "joke")
  public String joke;
}
