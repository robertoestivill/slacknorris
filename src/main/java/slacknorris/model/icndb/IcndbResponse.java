package slacknorris.model.icndb;

import com.google.gson.annotations.SerializedName;

public class IcndbResponse {

  @SerializedName("type")
  public String type;

  @SerializedName("value")
  public IcndbQuote value;
}
