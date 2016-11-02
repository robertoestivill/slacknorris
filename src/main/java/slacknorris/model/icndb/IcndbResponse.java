package slacknorris.model.icndb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IcndbResponse {

  @JsonProperty(value = "type")
  public String type;

  @JsonProperty(value = "value")
  public IcndbQuote value;
}
